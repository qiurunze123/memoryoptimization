### 亿级流量点击JVM调优

![图片](https://raw.githubusercontent.com/qiurunze123/imageall/master/jvm1015.png)

对于8G内存，我们一般是分配4G内存给JVM，正常的JVM参数配置如下：

‐Xms3072M ‐Xmx3072M ‐Xmn1536M ‐Xss1M ‐XX:PermSize=256M ‐XX:MaxPermSize=256M ‐XX:SurvivorRatio=8

![图片](https://raw.githubusercontent.com/qiurunze123/imageall/master/jvm1016.png)


    系统按每秒生成60MB的速度来生成对象，大概运行20秒就会撑满eden区，会出发minor gc，大
    概会有95%以上对象成为垃圾被回收，可能最后一两秒生成的对象还被引用着，我们暂估为
    100MB左右，那么这100M会被挪到S0区，回忆下动态对象年龄判断原则，这100MB对象同龄而
    且总和大于S0区的50%，那么这些对象都会被挪到老年代，到了老年代不到一秒又变成了垃圾对
    象，很明显，survivor区大小设置有点小
    我们分析下系统业务就知道，明显大部分对象都是短生存周期的，根本不应该频繁进入老年代，也
    没必要给老年代维持过大的内存空间，得让对象尽量留在新生代里
    
‐Xms3072M ‐Xmx3072M ‐Xmn2048M ‐Xss1M ‐XX:PermSize=256M ‐XX:MaxPermSize=256M ‐XX:SurvivorRatio=8

![图片](https://raw.githubusercontent.com/qiurunze123/imageall/master/jvm1018png)


    这样就降低了因为对象动态年龄判断原则导致的对象频繁进入老年代的问题，其实很多优化无非就
    是让短期存活的对象尽量都留在survivor里，不要进入老年代，这样在minor gc的时候这些对象
    都会被回收，不会进到老年代从而导致full gc。
    对于对象年龄应该为多少才移动到老年代比较合适，本例中一次minor gc要间隔二三十秒，大多
    数对象一般在几秒内就会变为垃圾，完全可以将默认的15岁改小一点，比如改为5，那么意味着对
    象要经过5次minor gc才会进入老年代，整个时间也有一两分钟了，如果对象这么长时间都没被回
    收，完全可以认为这些对象是会存活的比较长的对象，可以移动到老年代，而不是继续一直占用
    survivor区空间。
    对于多大的对象直接进入老年代(参数-XX:PretenureSizeThreshold)，这个一般可以结合你自己系统
    看下有没有什么大对象生成，预估下大对象的大小，一般来说设置为1M就差不多了，很少有超过
    1M的大对象，这些对象一般就是你系统初始化分配的缓存对象，比如大的缓存List，Map之类的
    对象
    
  可以适当调整JVM参数如下：
  ‐Xms3072M ‐Xmx3072M ‐Xmn2048M ‐Xss1M ‐XX:PermSize=256M ‐XX:MaxPermSize=256M ‐XX:SurvivorRa
  tio=8
  
  ‐XX:MaxTenuringThreshold=5 ‐XX:PretenureSizeThreshold=1M ‐XX:+UseParNewGC ‐XX:+UseConcMarkSw
  eepGC
  
    对于老年代的CMS的参数如何设置我们可以思考下 首先我们想当前的这个系统那些对象可能会长期躲过5次以上的GC呢 无非就是那些
    长期存活的躲过了5次以上的minor gc 最终进入到了老年代
    无非就是sping 容器的bean 线程池对象 一些初始化缓存对象等 这些加起来充其量也就是几十MB
    还有就是某次minor gc完了之后还有超过200M的对象存活，那么就会直接进入老年代，比如突然
    某一秒瞬间要处理五六百单，那么每秒生成的对象可能有一百多M，再加上整个系统可能压力剧
    增，一个订单要好几秒才能处理完，下一秒可能又有很多订单过来。
    我们可以估算下大概每隔五六分钟出现一次这样的情况，那么大概半小时到一小时之间就可能因为
    老年代满了触发一次Full GC，Full GC的触发条件还有我们之前说过的老年代空间分配担保机制，
    历次的minor gc挪动到老年代的对象大小肯定是非常小的，所以几乎不会在minor gc触发之前由
    于老年代空间分配担保失败而产生full gc，其实在半小时后发生full gc，这时候已经过了抢购的最
    高峰期，后续可能几小时才做一次FullGC。
    对于碎片整理，因为都是1小时或几小时才做一次FullGC，是可以每做完一次就开始碎片整理
    
    综上，只要年轻代参数设置合理，老年代CMS的参数设置基本都可以用默认值，如下所示：
    ‐Xms3072M ‐Xmx3072M ‐Xmn2048M ‐Xss1M ‐XX:PermSize=256M ‐XX:MaxPermSize=256M ‐XX:SurvivorRa
    tio=8
    ‐XX:MaxTenuringThreshold=5 ‐XX:PretenureSizeThreshold=1M ‐XX:+UseParNewGC ‐XX:+UseConcMarkSw
    eepGC
    ‐XX:CMSInitiatingOccupancyFaction=92 ‐XX:+UseCMSCompactAtFullCollection ‐XX:CMSFullGCsBefore
    Compaction=0
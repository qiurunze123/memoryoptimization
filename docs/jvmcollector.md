### 垃圾收集器

![图片](https://raw.githubusercontent.com/qiurunze123/imageall/master/jvm1010.png)

#### Serial收集器（-XX:+UseSerialGC -XX:+UseSerialoldGC）

    Serial（串行）收集器是最基本、历史最悠久的垃圾收集器了。大家看名字就知道这个收集器是一
    个单线程收集器了。它的 “单线程” 的意义不仅仅意味着它只会使用一条垃圾收集线程去完成垃
    圾收集工作，更重要的是它在进行垃圾收集工作的时候必须暂停其他所有的工作线程（ "Stop
    The World" ），直到它收集结束
    
    新生代采用复制算法 老年代采用标记整理算法
    
![图片](https://raw.githubusercontent.com/qiurunze123/imageall/master/jvm1011.png)


    Serial收集器由于没有线程交互的开销，自然可以获得很高的单线程收集效率。
    Serial Old收集器是Serial收集器的老年代版本，它同样是一个单线程收集器。它主要有两大用
    途：一种用途是在JDK1.5以及以前的版本中与Parallel Scavenge收集器搭配使用，另一种用途是
    作为CMS收集器的后备方案
    
#### ParNew收集器（-XX:+UseParNewGC）

ParNew收集器其实就是Serial收集器的多线程版本 除了使用多线程进行的垃圾收集外 其余的行为和Serial收集器一样 默认
的收集线程数根CPU 核数相同 当然可以用参数-XX:ParallelGCThreads 指定收集线程数 但是一般不推荐修改

新生代采用复制算法 老年代采用标记整理算法

![图片](https://raw.githubusercontent.com/qiurunze123/imageall/master/jvm1012.png)

它是许多运行在Server模式下的虚拟机的首要选择，除了Serial收集器外，只有它能与CMS收集器
（真正意义上的并发收集器，后面会介绍到）配合工作

#### Parallel Scavenge收集器(-XX:+UseParallelGC(年轻代),-XX:+UseParallelOldGC(老年代))

Parallel Scavenge 收集器类似于ParNew 收集器，是Server 模式（内存大于2G，2个cpu）下的
默认收集器，那么它有什么特别之处呢

Parallel Scavenge 收集器关注点是吞吐量（高效率利用CPU） CMS等垃圾收集器的关注点更多的是用户线程的停顿时间
所谓的吞吐量就是CPU中用于运行用户代码的时间和CPU总消耗的时间的对比  

    Parallel Scavenge收集器提供了很多参数供用户找到最合适的停顿
    时间或最大吞吐量，如果对于收集器运作不太了解的话，可以选择把内存管理优化交给虚拟机去完
    成也是一个不错的选择。
    新生代采用复制算法，老年代采用标记-整理算法
    
![图片](https://raw.githubusercontent.com/qiurunze123/imageall/master/jvm1013.png)

    Parallel Old收集器是Parallel Scavenge收集器的老年代版本。使用多线程和“标记-整理”算
    法。在注重吞吐量以及CPU资源的场合，都可以优先考虑 Parallel Scavenge收集器和Parallel
    Old收集器
    
    
#### CMS 收集器（-XX:+UseConcMarkSweepGC(old)）

CMS（Concurrent Mark Sweep）收集器是一种以获取最短回收停顿时间为目标的收集器。它
非常符合在注重用户体验的应用上使用，它是HotSpot虚拟机第一款真正意义上的并发收集器，
它第一次实现了让垃圾收集线程与用户线程（基本上）同时工作

从名字上的Mark Sweep 这俩个词可以看出 CMS 收集器是使用标记--清除算法实现的 他的运作过程相比较于前面几种
垃圾回收器来说更加复杂一旦 整个过程分为四步：

    初始标记： 暂停所有的其他线程，并记录下gc roots直接能引用的对象，速度很快 
    
    并发标记： 同时开启GC和用户线程，用一个闭包结构去记录可达对象。但在这个阶段
    结束，这个闭包结构并不能保证包含当前所有的可达对象。因为用户线程可能会不断的更新
    引用域，所以GC线程无法保证可达性分析的实时性。所以这个算法里会跟踪记录这些发生引
    用更新的地方
    
    重新标记： 重新标记阶段就是为了修正并发标记期间因为用户程序继续运行而导致标记
    产生变动的那一部分对象的标记记录，这个阶段的停顿时间一般会比初始标记阶段的时间稍
    长，远远比并发标记阶段时间短
    
    并发清理： 开启用户线程，同时GC线程开始对未标记的区域做清扫

![图片](https://raw.githubusercontent.com/qiurunze123/imageall/master/jvm1014.png)

主要优点： 并发收集 低停顿 


    对CPU资源敏感（会和服务抢资源）；
    无法处理浮动垃圾(在并发清理阶段又产生垃圾，这种浮动垃圾只能等到下一次gc再清理了)
    它使用的回收算法-“标记-清除”算法会导致收集结束时会有大量空间碎片产生，当然
    通过参数-XX:+UseCMSCompactAtFullCollection 可以让jvm在执行完标记清除后再做整
    理
    执行过程中的不确定性，会存在上一次垃圾回收还没执行完，然后垃圾回收又被触
    发的情况，特别是在并发标记和并发清理阶段会出现，一边回收，系统一边运行，也许没回
    收完就再次触发full gc，也就是"concurrent mode failure"，此时会进入stop the
    world，用serial old垃圾收集器来回收
    
    
    CMS的相关参数
    1. -XX:+UseConcMarkSweepGC：启用cms
    2. -XX:ConcGCThreads：并发的GC线程数
    3. -XX:+UseCMSCompactAtFullCollection：FullGC之后做压缩整理（减少碎片）
    4. -XX:CMSFullGCsBeforeCompaction：多少次FullGC之后压缩一次，默认是0，代表每次
    FullGC后都会压缩一次
    5. -XX:CMSInitiatingOccupancyFraction: 当老年代使用达到该比例时会触发FullGC（默认
    是92，这是百分比）
    6. -XX:+UseCMSInitiatingOccupancyOnly：只使用设定的回收阈值(-
    XX:CMSInitiatingOccupancyFraction设定的值)，如果不指定，JVM仅在第一次使用设定
    值，后续则会自动调整
    7. -XX:+CMSScavengeBeforeRemark：在CMS GC前启动一次minor gc，目的在于减少
    老年代对年轻代的引用，降低CMS GC的标记阶段时的开销，一般CMS的GC耗时 80%都在
    remark阶段
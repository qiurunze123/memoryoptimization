### 前言
生产环境内存性能调优方法与代码实践！
 ＱＱ:3341386488
 邮箱：QiuRunZe_key@163.com

我会不断完善，希望大家有好的想法拉一个分支提高，一起合作！


    觉得不错对您有帮助，麻烦右上角点下star以示鼓励！长期维护不易 多次想放弃 坚持是一种信仰 专注是一种态度！


## 本项目主要解决以下几个问题？？
     1.生产环境发生了内存溢出该如何处理 
     2.生产环境应该给服务器分配多少的内存 
     3.如何对垃圾回收起的性能进行调优 
     4.生产环境CPU的负载飙高应该如何处理
     5.生产环境因该给应用分配多少的线程合适
     6.不加log如何确定请求是否执行到某一段的代码
     7.实时查看某个方法的入参与返回值<
     8.JVM的字节码是什么东
     9.循环体中字符串拼接为什么效率很低
     10.string常量池怎么回事
     11.i++，++i 到底哪中效率高
## 本项目主要收获
    1.熟练使用各种监控和调试工具
    2.从容应对生产环境的各种调试和性能
    3.熟悉JVM字节码指令
    4.深入理解JVM自动回收机制学会GC调优

## 本项目主要收获
    1.基于JDK命令行的监控 
    JVM的参数类型
    查看JVM运行时的参数
    Jstat查看JVM统计信息
    演示内存溢出
    导出内存映像文件
    MAT分析内存溢出
    jstack与线程的状态
    jstack实战死循环与锁
    --------------------------------------||||
    2.基于JVisualVM的可视化工具 
    监控本地java进程
    监控远程java进程
    3.基于Btrace的监控调试 
    Btrace入门
    拦截器构造函数，同名函数
    拦截器返回值，异常，行号
    拦截器复杂参数，环境变量，正则匹配拦截
    注意事项
    --------------------------------------|||||
    4.tomcat的性能监控调优 
    tomcat远程debug
    tomcat-manager监控
    psi-probe监控
    tomcat优化
    --------------------------------------|||||
    5.nginx的监控调优 
    nginx安装
    ngx_http_stub_status监控连接信息
    ngxtop监控请求信息
    nginx-rrd图形化监控
    nginx优化
    ---------------------------------------|||||
    6.jvm+gc调优 
    JVM的内存结构
    
   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/JVM.png)
   
   ![整体流程](https://raw.githubusercontent.com/qiurunze123/imageall/master/JVM1.png)

    
    程序计数器：
    JVM支持多线程同时执行，每一个线程都有自己的PC Register 线程正在执行的方法叫做当前方法，如果是java代码，Pc register俩面存放的就是当前
    正在执行的指令的地址，如果是C 则是空
    
    虚拟机栈（JVM stacks）：
    虚拟机栈是线程私有的，他的生命周期和线程相同，虚拟机栈描述的事java方法执行的内存模型，每个方法在执行的时候都会创造一个栈帧，
    用于存储局部变量表，操作数栈，动态链接，方法出口等信息，每一个方法从调用直至执行的完成的过程，就对应着一个栈帧在虚拟机栈中入栈到出栈的过程
    把线程的堆栈打印出来说的就是虚拟机栈
    
    Heap -------->> 
    堆：java虚拟机所管理的内存中最大的一块，堆是被所有线程共享的一块内存区域，在虚拟机启动时创建，此内存区域的唯一目的就是存放对象的实例，几乎所有的
    对象实力都在这里分配内存
    java堆可以处在物理上不连续的内存空间中，只要逻辑上是连续的即可
    
    
    方法区Method Area
    方法区和堆一样，是各个线程共享的内存区域，它用于存储已被虚拟机栈加载的类信息，常量，静态变量，即时编译器编译后的代码等数据，虽然java的虚拟机规范
    把方法区描述为堆得一个逻辑部分，但是它却有一个别名叫做Non-Heap （非堆）目的是与java堆来区分开来
    ||||||  常量池： Run-Time- Constant Pool 运行时常量池是方法区的一部分，Class 文件中除了有类的版本，字段，方法，接口等描述信息外，还有
    ||||||  一项信息是常量池，用于存放编译期生成的各种字面量和符号引用，这部分内容将在类加载后进入方法区的运行时常量池存放
    
    本地方法栈-------->>
    
    ||||||||||||||||||||
    
    JDK1.8
    S0 S1 Suvivor区在同一时间只有一个有数据 ---- CCS 压缩类空间 短指针 需启动参数 --codecache存放编译的一些即时代码 等 
    
    Metaspace = Class Package Method Field 字节码 常量池 符号引用等
    
    CCS = 32位指针的class  codecache = JIT编译后的本地代码，JNI使用的C代码
    
    jstat -gc pid 
     S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU     CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT   
     1   600.0 1600.0  0.0    49.5  13184.0   6495.0   32728.0    20751.4   41088.0 39520.1 4992.0 4600.3    347    1.150   3      0.178    1.328
    -XX:-UserCompressedClassPointers  禁用短指针 
   
     CCSU区域会变为0相反的前面的meta会增加因为短指针被禁用掉，则转向长指针
     
     -Xms -Xmx 最小堆内存最大堆内存 
     -XX：NewSize -XX:MaxNewSize young区大小
     -XX:NewRatio -XX:SurvivorRatio new 与old的比例 eden与survivor比例
     -XX:MetaspaceSize -XX:MaxMetaSpaceSize 大小
     -XX:+UserCompressedClassPointers 是否禁用短指针
     -XX：CompressedClassSpaceSize 设置压缩类空间大小
     -XX:InitialCodeCatchSize codecache的大小
     
     
     ----------------------------------------------------------->>>>>>>>>
     垃圾回收算法
     
     标记清除
     可达性分析
     复制算法 S0S1
     标记整理 
     分代 
     
     Young区  --- 复制算法
     Old区 ----- 标记清除和标记整理
     
     
     

    
    
    
    
    常见的垃圾回收算法
    垃圾回收器
    GC日志格式详解
    可视化工具分析GC日志
    ParallelGC调优
    G1调优
    ----------------------------------------|||||
    
    
    代码规范本人-----------------------------------------------------------||||||
    1.长的类名会使开发者不易生命该类型的变量
    2.长的方法命名会使它变得晦涩难懂
    3.长的变量名不利于代码重用，导致过长的方法链
    
    
    
    命名俩个目标：
    ① 清晰: 你要知道该命名于什么有关
    ② 精确：你要知道该命名于什么无关
    当一个命名完成上面两个目标后，其余的字符就是多余的了
    
    
    
    命名无需含有表示变量或者参数类型的单词 
    ---- nameString -- name
    ---- accountLessWindow ---- window 
    
    对于集合来说，最好使用名词的复数形式来描述内容
    ---- List<DateTime> holidayDateLists -------- List<DateTime> holidays
    ---- Map<Employee,Role> employeeRoleHashMap ------ Map<Employee,Role> employeeRoles
    
    方法名不需要描述它的参数及参数的类型–参数列表已经说明了这些
    
    ---- mergeTableCells(List<TableCell> cells) --- merge(List<TableCell> cells)
    ---- sortEventsUsingComparator(List<Event> events,
             Comparator<Event> comparator) ---- sort(List<Event> events, Comparator<Event> comparator)
             
    省略命名中不是用来消除歧义的单词
    有些开发者喜欢将他们知道的变量的所有信息都塞到命名里面，记住命名只是一个标识符，只要告诉你得变量在哪定义的，并不是
    用来告诉阅读者所有他们想知道有关这个对象的详细信息，这是定义该做的事情，命名只是让你找到他的定义
    --------------recentlyUpdatedAnnualSalesBid
    
    存在不是最近更新的全年销售投标么？
    存在没有被更新的最近的全年销售投标么？
    存在最近更新的非全年的销售投标么？
    存在最近更新的全年非销售的投标么？
    存在最近更新的全年销售非投标的东东吗？
    
    上面的任何一个问题回答是不存在，那就意味着命名中引入了无用的单词
    ---- finalBattleMostDangerousBossMonster ------boss
    ---- weaklingFirstEncounterMonster ----- firstMonster
    
    如果有一些你觉得过了，太短了，容易引起歧义，但是你可以大胆的这样做，如果在之后的开发中你觉得命名会造成冲突和不明确
    你可以填一些修饰词来完善它，反之如果一开始就是一个很长的名字，你不可能再改回来
    
    
    省略命名中可以从上下文获取的单词
    
    // Bad:
    class AnnualHolidaySale {
      int _annualSaleRebate;
      void promoteHolidaySale() { ... }
    }
    
    // Better:
    class AnnualHolidaySale {
      int _rebate;
      void promote() { ... }
    }
    实际上一个命名嵌套的层次越多，他就有更多的相关的上下文，也就更简短，换句话说一个变量的作用域越小，命名就越短
    
    省略命名中无任何意义的单词
    
    一些开发者喜欢在命名中加入一些严肃的单词，但是这种毫无必要
    例如：data、state、amount、value、manager、engine、object、entity 和 instance一类的 就不需要 
    
    一个好的命名能够在阅读者的脑海里面描绘出一幅图画，而降变量命名为manager 并不能象读者传达出任何有关该变量做什么的信息----
    
    在命名时可以问一下自己，把这个单词去掉含义是不是不变？如果是，那就果断把它剔除吧
    
    
    例子:------------------------------------------------------------------>>>>>>好吃的华夫饼
    
    // 好吃的比利时华夫饼
    class DeliciousBelgianWaffleObject {
      void garnishDeliciousBelgianWaffleWithStrawberryList(
          List<Strawberry> strawberryList) { ... }
    }
    
    ----------------------------------------------------------------------->>>>>> 
    
    首先，通过参数列表，我们可以知道方法是用来处理一个strawberry 的列表 所以可以在方法的命名中去掉
    
    class DeliciousBelgianWaffleObject {
        void garnishDeliciousBelgianWaffle(
            List<Strawberry> strawberries) { ... }
    }
    
    除非程序中还包含不好吃的比利时华夫饼或者其它华夫饼 不然我们可以将这个无用的形容词去掉------
    
    class WaffleObject {
      void garnishWaffle(List<Strawberry> strawberries) { ... }
    }
    
    ------------------------------------------------------------------------->>>>>>>
    
    方法是包含在waffleObject类中的 所以方法名无需waffle说明
    class WaffleObject {
      void garnish(List<Strawberry> strawberries) { ... }
    }
    -------------------------------------------------------------------------->>>>>>>
    很明显他是一个对象，任何事物都是一个对象，这也就是传说中的面相对象的意思，所以命名中无需带有Object
    class Waffle {
      void garnish(List<Strawberry> strawberries) { ... }
    }
    
    -------------------------------------------------------------------------->>>>>>>
    类名应该是名词不应该是动词    ---- 使用普遍的被大众理解的词
    
    
    
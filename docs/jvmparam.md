### JVM简单参数 

#### jinfo 命令 
    jinfo  查看是否开启此Gc日志
    
    jinfo -flag UseConcMarkSweepGC 23108 
    
    jinfo -flag MaxHeapSize 进程id
    
    jinfo -flag ThreadStackSize 进程id 线程堆栈大小


#### java 命令 
    
    一种是Boolean 类型 
    格式：-XX：[+-] <name> 表示启用或者禁用name属性
    比如：-XX:++UseConcMarkSweepGC  -XX:+UseG1GC
    另一种是非boolean 类型
    格式 -XX:<name>=<value> 表示name属性的值得value
    比如：-XX:MaxGCPauseMillis=500
    XX:GCTimeRation = 19 
    -Xmx -Xms
    -Xms 等价于 -XX:lnitialHeapSize
    -Xmx 等价于 -XX:MaxHeapSize
    
    java -XX:+PrintFlagsFinal -version
    查看JVM 运行时的参数
    -XX:+PrintFlagslnitial
    -XX:+PrintFlagsFinal
    -XX:+UnlockExperimentalVMOptions 解锁实验参数
    -XX:+PrintCommandLineFlags 打印命令行参数
    =初始化的值 
    :=被修改过的值

#### jps 命令 

查询进程id 

#### jstat 查看JVM 统计信息

    类装载信息 垃圾回收信息  JIT编译信息 
    查看类装载信息 
    jstat -class id 1000 10
    查看GC垃圾回收信息
    jstat -gc 进程id 1000 10 

#### 内存溢出问题

##### 自动设置内存溢出
    -XX:+HeapDumpOnOutOfMemoryError
    -XX:HeapDumpPath = 设置的目录
    但是自动设置内存溢出发现问题较慢已经为时已晚

##### jmap 手动导出内存

    jmap -dump:format=b,file=heap.hprof 进程id

##### 如何分析导出内存 

    有一个MAT 分析工具 可以直接将文件导入分析和定位内存溢出 （直接找强引用多的就可以了）
    可以联系我发给你 qq看readme !

#### 如何定位内存CPU高生和死锁的问题

##### jstack 命令

    1.top 一下 看看那个进程的cpu 负载率较高
    2. jstack 进程id > xxxx.txt 导出发生的日志
    3.top -p pid -H 可以查找最近那些线程的负载比较高
    4.pringf "%x" pid 将进程号进制转换下 可以直接在日志查找搞这个的进程号
    5.搜索定位问题

##### 程序死锁

found 1 dead lock 


#### 数据库死锁
    1.查找日志 sql错误
    
    2.mysql中使用命令：SHOW ENGINE INNODB STATUS;
    总能获取到最近一些问题信息，通过搜索deadlock 关键字即可找到死锁的相关日志信息。
    分析哪些语句申请锁资源冲突，结合1来确定对应的代码，通过使用分布式锁或者修改获取数据锁顺序来修复

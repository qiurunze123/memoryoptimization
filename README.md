![JVM内存调优与生产实战](https://raw.githubusercontent.com/qiurunze123/imageall/master/jvminit.png)

> 邮箱 : [QiuRunZe_key@163.com](QiuRunZe_key@163.com)

> Github : [https://github.com/qiurunze123](https://github.com/qiurunze123)

> QQ : [3341386488](3341386488)

> QQ群 : [705033624](705033624) 


####  本项目介绍 : JVM内存调优与生产实战 简单说明下 

在项目上线之初，我们应该如何设置JVM的参数配置,我们如何分配内存空间会使效率最大化，当项目上线后我们如何监控项目的内存情况呢？
我们又如何来查看内存的溢出情况，分析GC日志呢？...... 这个项目就是为了这些研究这些可能大家平时不会考虑的东西来应运而生? 
此项目仍为进阶课程,一些简单的请提前预习! 

希望大家能够有所收获！有问题请联系我!
 
## 本项目主要解决以下几个问题??

### 深入分析类加载机制--在分析JVM之前我们有必要分析一下类加载机制
### 线上生产定位问题（com.jvm.memory.controller 为演示死锁和内存溢出）
### jvm的参数简单了解
### JVM整体结构分析

| ID | Problem  | Article | 
 | --- | ---   | :--- |
 | 000 |JVM类加载机制深入分析 | [解决思路](/docs/jvm.md) |
 | 001 |JVM类加载过程 | [解决思路](/docs/jvm.md) |
 | 002 |永远不变的问题 -- 类加载器和双亲委派机制 | [解决思路](/docs/jvm.md) |
 | 003 |如何自定义一个类加载器 | [解决思路](/docs/jvm.md) |
 | 004 |为什么会有双亲委派机制 | [解决思路](/docs/jvm.md) |
 | 005 |如何打破双亲委派机制 | [解决思路](/docs/jvm.md) |
 | 006 |tomcat如何打破双亲委派机制 | [解决思路](/docs/jvm.md) |
 | 007 |JVM简单参数 | [解决思路](/docs/jvmparam.md) |
 | 008 |JVM进阶参数 | [解决思路](/docs/jvmparam.md) |
 | 009 |一些参数使用方式 | [解决思路](/docs/jvmparam.md) |
 | 010 |如何导出内存溢出文件 | [解决思路](/docs/jvmparam.md) |
 | 011 |如何分析内存溢出文件 | [解决思路](/docs/jvmparam.md) |
 | 012 |如何定位cpu飙高问题 | [解决思路](/docs/jvmparam.md) |
 | 013 |如何定位死锁问题 | [解决思路](/docs/jvmparam.md) |
 | 014 |栈运行机制 | [解决思路](/docs/jvmstack.md) |
 | 015|JVM整体结构 | [解决思路](/docs/jvmstack.md) |
 | 016 |栈内存溢出问题--栈深度问题 | [解决思路](/docs/jvmstack.md) |
 | 017 |JVM内存调优初步认识 | [解决思路](/docs/jvmstack.md) |
 | 018 |JVM对象逃逸分析| [解决思路](/docs/jvmstack.md) |
 | 019 |JVM如何配合生产环境设置参数 | [解决思路](/docs/jvmsee.md) |
 | 020 |百万级流量设置JVM参数 （初始化） | [解决思路](/docs/jvmsee.md) |
 | 021 |JVM内存分配与回收 | [解决思路](/docs/jvmsee.md) |
 | 022 |对象优先在Eden区分配 | [解决思路](/docs/jvmsee.md) |
 | 023 |大对象直接进入老年代 | [解决思路](/docs/jvmsee.md) |
 | 024 |长期存活的对象会进入老年代 | [解决思路](/docs/jvmsee.md) |
 | 025 |对象动态年龄判断 | [解决思路](/docs/jvmsee.md) |
 | 026 |老年代空间分配担保机制 | [解决思路](/docs/jvmsee.md) |
 | 027 |如何判断对象是否可以被回收 | [解决思路](/docs/jvmsee.md) |
 | 028 |垃圾收集算法 | [解决思路](/docs/jvmsee.md) |
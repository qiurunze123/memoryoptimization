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

| ID | Problem  | Article | 
 | --- | ---   | :--- |
 | 000 |JVM类加载机制深入分析 | [解决思路](/docs/jvm.md) |
 | 001 |JVM类加载过程 | [解决思路](/docs/jvm.md) |
 | 002 |永远不变的问题 -- 类加载器和双亲委派机制 | [解决思路](/docs/jvm.md) |
 | 003 |如何自定义一个类加载器 | [解决思路](/docs/jvm.md) |
 | 004 |为什么会有双亲委派机制 | [解决思路](/docs/jvm.md) |
 | 005 |如何打破双亲委派机制 | [解决思路](/docs/jvm.md) |
 | 006 |tomcat如何打破双亲委派机制 | [解决思路](/docs/jvm.md) |
 
### jvm的参数简单了解

 | ID | Problem  | Article | 
 | --- | ---   | :--- |
 | 000 |JVM简单参数 | [解决思路](/docs/jvmparam.md) |
 | 001 |JVM进阶参数 | [解决思路](/docs/jvmparam.md) |
 | 002 |一些参数使用方式 | [解决思路](/docs/jvmparam.md) |
 
### 线上生产定位问题（com.jvm.memory.controller 为演示死锁和内存溢出）

| ID | Problem  | Article | 
 | --- | ---   | :--- |
 | 000 |如何导出内存溢出文件 | [解决思路](/docs/jvmparam.md) |
 | 001 |如何分析内存溢出文件 | [解决思路](/docs/jvmparam.md) |
 | 002 |如何定位cpu飙高问题 | [解决思路](/docs/jvmparam.md) |


### JVM整体结构分析

| ID | Problem  | Article | 
 | --- | ---   | :--- |
 | 000 |栈运行机制 | [解决思路](/docs/jvmstack.md) |
 | 001 |JVM整体结构 | [解决思路](/docs/jvmstack.md) |
 | 002 |栈内存溢出问题--栈深度问题 | [解决思路](/docs/jvmstack.md) |
 | 003 |JVM内存调优初步认识 | [解决思路](/docs/jvmstack.md) |
 | 002 |如何定位死锁问题 | [解决思路](/docs/jvmparam.md) |

### JVM  内存模型深入研究

| ID | Problem  | Article | 
 | --- | ---   | :--- |
 | 000 |JVM整体结构概览 | [解决思路](/docs/jvmsee.md) |
 | 001 |JVM如何配合生产环境设置参数 | [解决思路](/docs/jvmsee.md) |


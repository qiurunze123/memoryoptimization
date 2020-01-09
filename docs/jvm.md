## 开篇章 深入分析类加载过程

### **类加载过程深入分析**

#### 类加载过程

当项目的java文件经过编译打包生成可运行的jar 具体项目可直接用memory 最后用java命令运行某个主类的main函数
启动程序 如果主类加载的过程中需要用到别的类会逐步在加载别的类 

**jar包里面的类不是一次性记载到内存中的而是使用的时候在加载**


#### 类加载步骤

![图片](https://raw.githubusercontent.com/qiurunze123/imageall/master/jvm1000.png)

    1.加载 
    在磁盘中查找并通过IO读入字节码文件，使用类的时候才会加载 比如调用main() , 创建对象 new Object()等
    2.验证
    校验字节码文件的正确性
    3.准备
    给类的静态变量分配内存，并给静态变量赋值
    4.解析
    将符号引用替换为直接引用，该阶段会把一些静态方法(符号引用，比如
    main()方法)替换为指向数据所存内存的指针或句柄等(直接引用)，这是所谓的静态链
    接过程(类加载期间完成)，动态链接是在程序运行期间完成的将符号引用替换为直接
    引用
    5.初始化
    对类的静态变量初始化为指定的值，执行静态代码块
    6.使用 ......
    7.卸载 ......
    
### **双亲委派机制**

#### 类加载器的种类

![图片](https://raw.githubusercontent.com/qiurunze123/imageall/master/jvm1001.png)

JdkClassLoderTest 测试类 

    运行结果：
     null //启动类加载器是C++语言实现，所以打印不出来
     sun.misc.Launcher$ExtClassLoader
     sun.misc.Launcher$AppClassLoader
     sun.misc.Launcher$AppClassLoader
     
#### 如何自定义一个类加载器

MyClassLoaderTest 继承classloader 大家可以发现每一个类加载器都会有一个findClass方法 每个
类加载去就是通过这样来找寻class 

##### 操作

         /**
         * 把user 复制一份 user1 到 D:/bill
         * 会发现 如果现在程序里面没有 user1 则结果为
         *  ****** 我是自己的类加载器 ******
         * com.jvm.memory.classLoaderT.MyClassLoaderTest$MyClassLoader
         * 要是 程序里面已经有了user1 则为
         *  ****** 我是自己的类加载器 ******
         * sun.misc.Launcher$AppClassLoader 应用程序加载器
         * 说明先向上委派 因为应用程序已经存在了则 只能是应用程序类加载器
         * 双亲委派机制前瞻
         */

### 什么是双亲委派机制 

#### 委派流程说明 

![图片](https://raw.githubusercontent.com/qiurunze123/imageall/master/jvm1001.png)

    这里类加载其实就有一个双亲委派机制，加载某个类时会先委托父加载器寻找目标类，找不
    到再委托上层父加载器加载，如果所有父加载器在自己的加载类路径下都找不到目标类，则
    在自己的类加载路径中查找并载入目标类。
    比如我们的User类，最先会找应用程序类加载器加载，应用程序类加载器会先委托扩展类
    加载器加载，扩展类加载器再委托启动类加载器，顶层启动类加载器在自己的类加载路径里
    找了半天没找到User类，则向下退回加载User类的请求，扩展类加载器收到回复就自己加
    载，在自己的类加载路径里找了半天也没找到User类，又向下退回User类的加载请求给应
    用程序类加载器，应用程序类加载器于是在自己的类加载路径里找User类，结果找到了就
    自己加载了
    双亲委派机制说简单点就是，先找父亲加载，不行再由儿子自己加载
    
### 为什么需要设计双亲委派机制

    沙箱安全机制：自己写的java.lang.String.class类不会被加载，这样便可以防止
    核心API库被随意篡改（ 可以自己写一个String 类 会发现报错 ）
     
    避免类的重复加载：当父亲已经加载了该类时，就没有必要子ClassLoader再加
    载一次，保证被加载类的唯一性
    
### 如何打破双亲委派机制
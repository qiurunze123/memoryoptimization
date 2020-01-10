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
    
### 为什么需要打破双亲委派机制
     
     1.有时候比如tomcat 就是属于一个web 程序可能部署俩个应用程序 
     不同的应用程序可能会依赖一个第三方类库的不同版本 不能要求同一个类库在
     同一个服务器只有一份 因此要保证每个应用程序的类库都是独立的 保证相互隔离

#### 注意

加载到jvm 的话 不仅仅会看这个类名字是不是一个 还要看是不是一个类加载器  的同一个实例


### 举例tomcat 需要解决什么问题

    1. 一个web容器可能需要部署两个应用程序，不同的应用程序可能会依赖同一个第三方类
    库的不同版本，不能要求同一个类库在同一个服务器只有一份，因此要保证每个应用程序的
    类库都是独立的，保证相互隔离。
    2. 部署在同一个web容器中相同的类库相同的版本可以共享。否则，如果服务器有10个应
    用程序，那么要有10份相同的类库加载进虚拟机。
    3. web容器也有自己依赖的类库，不能与应用程序的类库混淆。基于安全考虑，应该让容
    器的类库和程序的类库隔离开来。
    4. web容器要支持jsp的修改，我们知道，jsp 文件最终也是要编译成class文件才能在虚拟
    机中运行，但程序运行后修改jsp已经是司空见惯的事情， web容器需要支持 jsp 修改后不
    用重启
#### 为什么tomcat 不能使用双亲委派机制

![图片](https://raw.githubusercontent.com/qiurunze123/imageall/master/jvm1004.png)


    如果使用默认的类加载器机制，那么是无法加载两个相同类库的不同版本的，
    默认的类加器是不管你是什么版本的，只在乎你的全限定类名，并且只有一份。第二个问
    题，默认的类加载器是能够实现的，因为他的职责就是保证唯一性。
    第三个问题和第一个问题一样
    
    我们再看第四个问题，我们想我们要怎么实现jsp文件的热加载，jsp 文件其实也就是class
    文件，那么如果修改了，但类名还是一样，类加载器会直接取方法区中已经存在的，修改后
    的jsp是不会重新加载的。那么怎么办呢？我们可以直接卸载掉这jsp文件的类加载器，所以
    你应该想到了，每个jsp文件对应一个唯一的类加载器，当一个jsp文件修改了，就直接卸载
    这个jsp类加载器。重新创建类加载器，重新加载jsp文件

#### tomcat类加载器结构 

![图片](https://raw.githubusercontent.com/qiurunze123/imageall/master/jvm1005.png)

    tomcat的几个主要类加载器：
    commonLoader：Tomcat最基本的类加载器，加载路径中的class可以被
    Tomcat容器本身以及各个Webapp访问
    catalinaLoader：Tomcat容器私有的类加载器，加载路径中的class对于
    Webapp不可见
    sharedLoader：各个Webapp共享的类加载器，加载路径中的class对于所有
    Webapp可见，但是对于Tomcat容器不可见；
    WebappClassLoader：各个Webapp私有的类加载器，加载路径中的class只对
    当前Webapp可见
    
    
    从图中的委派关系中可以看出：
    CommonClassLoader能加载的类都可以被CatalinaClassLoader和SharedClassLoader使
    用，从而实现了公有类库的共用，而CatalinaClassLoader和SharedClassLoader自己能加
    载的类则与对方相互隔离。
    WebAppClassLoader可以使用SharedClassLoader加载到的类，但各个
    WebAppClassLoader实例之间相互隔离。
    而JasperLoader的加载范围仅仅是这个JSP文件所编译出来的那一个.Class文件，它出现的
    目的就是为了被丢弃：当Web容器检测到JSP文件被修改时，会替换掉目前的
    JasperLoader的实例，并通过再建立一个新的Jsp类加载器来实现JSP文件的热加载功能
    
#### tomcat 如何打破了双亲委派机制

双亲委派机制要求除了顶层的启动类加载器之外，其余的类加载器都应当由自己的父类加载器加载。
很显然，tomcat 不是这样实现，tomcat 为了实现隔离性，没有遵守这个约定，每个
webappClassLoader加载自己的目录下的class文件，不会传递给父类加载器，打破了双
亲委派机制。
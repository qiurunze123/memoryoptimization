package com.jvm.memory.classLoaderT;

/**
 * @author 邱润泽 bullock
 */
public class ClassLoaderTest {

    public static void main(String[] args) {

        Object object = null;
        //new 对象 这个才算是使用了 类才会加载
        object = new Object();
    }
}

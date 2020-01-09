package com.jvm.memory.classLoaderT;

import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * @author 邱润泽 bullock
 */
public class MyClassLoaderTest {

    static class MyClassLoader extends ClassLoader {
        private String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        private byte[] loadByte(String name) throws Exception {
            name = name.replaceAll("\\.", "/");
            FileInputStream fis = new FileInputStream(classPath + "/" + name
                    + ".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                //defineClass将一个字节数组转为Class对象，这个字节数组是class文件读取后最的字节数组。
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }


        /**
         * 把user 复制一份 user1 到 D:/bill
         * 会发现 如果现在程序里面没有 user1 则结果为
         *  ****** 我是自己的类加载器 ******
         * com.jvm.memory.classLoaderT.MyClassLoaderTest$MyClassLoader
         *
         * 要是 程序里面已经有了user1 则为
         *  ****** 我是自己的类加载器 ******
         * sun.misc.Launcher$AppClassLoader 应用程序加载器
         * 说明先向上委派 因为应用程序已经存在了则 只能是应用程序类加载器
         *
         *
         * @param args
         * @throws Exception
         */
        public static void main(String[] args) throws Exception {
            MyClassLoader classLoader = new MyClassLoader("D:/bill");


            Class clazz = classLoader.loadClass("com.jvm.memory.controller.User1");

            Object obj = clazz.newInstance();
            Method method = clazz.getDeclaredMethod("sout", null);

            method.invoke(obj, null);
            System.out.println(clazz.getClassLoader().getClass().getName());
        }
    }
}

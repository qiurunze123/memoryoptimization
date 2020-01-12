package com.jvm.memory.testStack;

import com.jvm.memory.controller.User;

public class Math {

    public static int initData = 666 ;

    public static User user = new User();

    //每一个方法对应一块单独的栈针内存区域
    public int compute(){
        int a=1;
        int b=1;
        int c = (a+b) *10;
        return c;
    }


    //比如main 方法执行的时候就会单独为main方法开辟出一块内存区域
    public static void main(String[] args) {
        Math math = new Math();
        math.compute();
    }
}

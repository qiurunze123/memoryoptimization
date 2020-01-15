package com.jvm.memory.testStack;

/**
 * @author 邱润泽 bullock
 */
public class TestG {

    public static void main(String[] args) {
        int i = 54321;
        int backspin = 0;

        while(i > 0){
            int tmp = i % 10;
            backspin = backspin * 10 + tmp;
            i = i / 10;
        }

        System.out.println(backspin);
    }
}

package com.jvm.memory.testStack;

/**
 * @auth qiurunze bullock
 * JVM -Xss 128k -Xss默认1M
 */
public class StackOverFlowTest {

    static int count = 0;

    static void redo() {
        count++;
        redo();
    }

    public static void main(String[] args) {
        try {
            redo();
        } catch (Throwable t) {
            t.printStackTrace();
            System.out.println(count);
        }
    }
}

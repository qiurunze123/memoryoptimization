package com.jvm.memory.testStack;

/**
 * @author 邱润泽 bullock
 */
public class ReferenceCountingGc {

    Object instance = null;
         public static void main(String[] args) {
             ReferenceCountingGc objA = new ReferenceCountingGc();
             ReferenceCountingGc objB = new ReferenceCountingGc();
             objA.instance = objB;
             objB.instance = objA;
             objA = null;
             objB = null;
         }
}

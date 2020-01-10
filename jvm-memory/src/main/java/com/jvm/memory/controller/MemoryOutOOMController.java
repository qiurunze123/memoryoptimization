package com.jvm.memory.controller;

import com.jvm.memory.service.Metaspace;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.jvm.memory.utils.StringFromUtils.getPartneridsFromJson;

@Controller
@RequestMapping("/memory")
public class MemoryOutOOMController {

    private List<Class<?>>  classList = new ArrayList<Class<?>>();

    private Object lock1 = new Object();
    private Object lock2 = new Object();


    /**
     * -Xmx32M -Xms32M
     */
    @RequestMapping(value = "/oom")
    public String user() {
        List<User> users = new ArrayList<>();
        int i =0;
        while(true){
            users.add(new User(UUID.randomUUID().toString(),i++));
        }
    }


    /**
     * -XX:MetaspaceSize=32M -XX:MaxMetaspaceSize=32M
     * */
    @GetMapping("/nonheap")
    public String nonheap() {
        while(true) {
            classList.addAll(Metaspace.createClasses());
        }
    }

    @RequestMapping(value = "deadLock")
    public List<Long> loopDead() {
        String data = "{\"data\":[{\"partnerid\":]";
            System.out.println("死循环");
        return getPartneridsFromJson(data);
    }


    /**
     * 死锁
     * */
    @RequestMapping("/deadlock")
    public String deadlock(){
        new Thread(()->{
            synchronized(lock1) {
                try {Thread.sleep(1000);}catch(Exception e) {}
                synchronized(lock2) {
                    System.out.println("Thread1 over");
                }
            }
        }) .start();
        new Thread(()->{
            synchronized(lock2) {
                try {Thread.sleep(1000);}catch(Exception e) {}
                synchronized(lock1) {
                    System.out.println("Thread2 over");
                }
            }
        }) .start();
        return "deadlock";
    }

}

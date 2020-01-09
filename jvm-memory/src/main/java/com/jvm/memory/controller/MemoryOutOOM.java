package com.jvm.memory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/memory")
public class MemoryOutOOM {


    @RequestMapping(value = "/oom")
    public String user() {
        List<User> users = new ArrayList<>();
        int i =0;
        while(true){
            users.add(new User(UUID.randomUUID().toString(),i++));
        }
    }

}

package com.javaproject.taskmanager;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String sayhello(){
        return "Hello World!";

    }
}

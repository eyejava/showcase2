package com.jade.showcase;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("hello")
public class HelloController {

    @RequestMapping("/world")
    public String hello(){
        return "hello world jade   code";

    }
}

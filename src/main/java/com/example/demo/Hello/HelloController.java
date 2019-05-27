package com.example.demo.Hello;


import com.example.demo.Hello.response.GreetingResponse;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HelloController {

    @CrossOrigin(origins = "*")
    @RequestMapping("/")
    public String index() {
        return "Hello, World!";
    }

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public @ResponseBody
    GreetingResponse greeting(@RequestParam(value = "name", defaultValue = "World") String name){
        return new GreetingResponse(counter.incrementAndGet(), "Hello, " + name + "!");
    }
}

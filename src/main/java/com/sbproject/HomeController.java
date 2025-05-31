package com.sbproject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    // Controller indicates it can be web controller for receiving web traffic

    @RequestMapping("/")
    public String index() {
        return "index.html";
    }
}
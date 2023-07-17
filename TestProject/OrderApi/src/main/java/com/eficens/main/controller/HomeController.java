package com.eficens.main.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/index")
    public String homepage(){

        return "index";

    }
    @RequestMapping("/")
    public String mainpage(){

        return "index";

    }

    @RequestMapping("/home")
    public String page(){

        return "index";

    }

}

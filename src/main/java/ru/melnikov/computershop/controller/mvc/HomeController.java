package ru.melnikov.computershop.controller.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @GetMapping
    public String homeController(){
        return "redirect:/product";
    }

    @PostMapping
    public void exitController(){
        System.exit(130);
    }
}

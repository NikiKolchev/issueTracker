package com.tracker.controller;

import com.mvcFramework.annotations.controller.Controller;
import com.mvcFramework.annotations.request.GetMapping;

import javax.ejb.Stateless;

@Controller
@Stateless
public class HomeController {

    @GetMapping("/")
    public String getHomePage() {
        return "/templates/home";
    }
}

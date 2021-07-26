package com.wmock.info.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class SwaggerController {

    @RequestMapping("/swagger")
    public String greeting() {
        return "redirect:/swagger-ui.html";
    }

}

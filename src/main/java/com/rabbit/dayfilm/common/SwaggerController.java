package com.rabbit.dayfilm.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {

    @GetMapping("/doc")
    public String redirectSwagger() {
        return "redirect:/swagger-ui/index.html";
    }
}
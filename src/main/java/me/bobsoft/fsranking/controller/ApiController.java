package me.bobsoft.fsranking.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin
@RestController
public class ApiController {

    @GetMapping("/api")
    public ModelAndView redirectToApi() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }
}

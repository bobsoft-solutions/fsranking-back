package me.bobsoft.fsranking;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/")
    public String testMethod() {
        return "FSRanking is alive!";
    }

}

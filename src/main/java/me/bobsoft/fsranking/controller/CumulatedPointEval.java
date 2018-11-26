package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.service.Eval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CumulatedPointEval {
    @Autowired
    private Eval eval;

    @CrossOrigin
    @GetMapping("/cumulated/point/eval")
    public String findPlayerById() {
        eval.eval("xyz");
        return "Done";
    }
}

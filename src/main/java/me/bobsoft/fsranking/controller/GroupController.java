package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.entities.Group;
import me.bobsoft.fsranking.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/groups")
    public List<Group> findAll() {
        return groupService.findAll();
    }
}

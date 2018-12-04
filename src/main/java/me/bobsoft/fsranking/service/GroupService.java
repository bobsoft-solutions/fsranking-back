package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.entities.Group;
import me.bobsoft.fsranking.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public List<Group> findAll() {
        return groupRepository.findAll();
    }
}

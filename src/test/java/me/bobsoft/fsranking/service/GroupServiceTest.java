package me.bobsoft.fsranking.service;

import me.bobsoft.fsranking.model.entities.Group;
import me.bobsoft.fsranking.repository.GroupRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GroupServiceTest {
    
    private GroupRepository groupRepository;

    private GroupService groupService;

    private List<Group> groups = new ArrayList<>();

    @Before
    public void setUp() {
        groupRepository = mock(GroupRepository.class);

        groupService = new GroupService(groupRepository);

        Group group = new Group();
        group.setId(1);
        group.setName("Berlin");

        groups.add(group);

        group = new Group();
        group.setId(2);
        group.setName("World Cup");

        groups.add(group);
    }

    @Test
    public void findAllTest1() {
        when(groupRepository.findAll()).thenReturn(new ArrayList<>());

        List<Group> groups = groupService.findAll();

        assertThat(groups).isNotNull();
        assertThat(groups.size()).isEqualTo(0);
    }

    @Test
    public void findAllTest2() {
        when(groupRepository.findAll()).thenReturn(groups);

        List<Group> groups = groupService.findAll();

        assertThat(groups).isNotNull();
        assertThat(groups.size()).isEqualTo(2);
        assertThat(groups).isEqualTo(this.groups);
    }

    @Test
    public void findAllTest3() {
        when(groupRepository.findAll()).thenReturn(groups);

        List<Group> groups = groupService.findAll();

        assertThat(groups).isNotNull();
        assertThat(groups.get(0).getClass()).isEqualTo(Group.class);
    }
}
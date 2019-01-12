package me.bobsoft.fsranking.controller;

import me.bobsoft.fsranking.model.entities.Group;
import me.bobsoft.fsranking.repository.GroupRepository;
import me.bobsoft.fsranking.service.GroupService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@SpringBootTest
//@RunWith(SpringRunner.class)
public class GroupControllerTest {

    private GroupService groupService;

    private GroupController groupController;

    private List<Group> groups = new ArrayList<>();

    @Before
    public void setUp() {
        groupService = mock(GroupService.class);

        groupController = new GroupController(groupService);

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
        when(groupController.findAll()).thenReturn(new ArrayList<>());

        List<Group> groups = groupController.findAll();

        assertThat(groups).isNotNull();
        assertThat(groups.size()).isEqualTo(0);

        Mockito.verify(groupService, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllTest2() {
        when(groupService.findAll()).thenReturn(groups);

        List<Group> groups = groupService.findAll();

        assertThat(groups).isNotNull();
        assertThat(groups.size()).isEqualTo(2);
        assertThat(groups).isEqualTo(this.groups);

        Mockito.verify(groupService, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllTest3() {
        when(groupService.findAll()).thenReturn(groups);

        List<Group> groups = groupService.findAll();

        assertThat(groups).isNotNull();
        assertThat(groups.get(0).getClass()).isEqualTo(Group.class);

        Mockito.verify(groupService, Mockito.times(1)).findAll();
    }
}
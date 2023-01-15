package org.example.MVCControllers;

import lombok.RequiredArgsConstructor;
import org.example.Models.Project;
import org.example.Models.User;
import org.example.Services.ProjectService;
import org.example.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProjectPageController {
    //todo сделать все посередине

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping("/projects/{id}")
    private String getProjectById(@PathVariable("id") Long ID, Principal principal, Model model) {
        Project project = projectService.findById(ID);
        User owner = userService.findByID(project.getAuthorId());
        User user = userService.findByUsername(principal.getName());
        if (owner.getUsername().equals(principal.getName())) {
            model.addAttribute("admin",true);
        }
        List<User> userInOrder = userService.findByIds(project.getOrderedUsers());
        model.addAttribute("project",project);
        model.addAttribute("tasks", project.getTasks());
        model.addAttribute("ordered", userInOrder);
        model.addAttribute("currentUser",user);
        model.addAttribute("users",project.getUser());
        return "projectPage";
    }

    @PostMapping("/task/{id}")
    private String addTaskToProject(@PathVariable("id") Long ID, Principal principal, String task, Model model) {
        Project project = projectService.findById(ID);
        project.getTasks().add(task);
        project = projectService.saveProject(project);
        User owner = userService.findByID(project.getAuthorId());
        if (owner.getUsername().equals(principal.getName())) {
            model.addAttribute("admin",true);
        }
        User user = userService.findByUsername(principal.getName());
        List<User> userInOrder = userService.findByIds(project.getOrderedUsers());
        model.addAttribute("ordered", userInOrder);
        model.addAttribute("currentUser",user);
        model.addAttribute("project",project);
        model.addAttribute("tasks", project.getTasks());
        model.addAttribute("users",project.getUser());
        return "projectPage";
    }

    @PostMapping("/deletetask/{id}")
    private String deleteTask(@PathVariable(name = "id") Long id, @RequestParam(name="taskVal") String task, Principal principal,Model model) {
        Project project = projectService.findById(id);
        project.getTasks().remove(task);
        project = projectService.saveProject(project);
        User owner = userService.findByID(project.getAuthorId());
        if (owner.getUsername().equals(principal.getName())) {
            model.addAttribute("admin",true);
        }
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("currentUser",user);
        List<User> userInOrder = userService.findByIds(project.getOrderedUsers());
        model.addAttribute("ordered", userInOrder);
        model.addAttribute("project",project);
        model.addAttribute("tasks", project.getTasks());
        model.addAttribute("users",project.getUser());
        return "projectPage";
    }

    @GetMapping("/join/{id}")
    public String joinToProject(@PathVariable("id") Long ID, Principal principal, Model model) {
        Project project = projectService.findById(ID);
        User wantToJoin = userService.findByUsername(principal.getName());
        if (project.getUser().contains(wantToJoin)) {
            model.addAttribute("alert","you are member");
        } else {
            project.getOrderedUsers().add(wantToJoin.getID());
            model.addAttribute("alert","you in order");
        }
        projectService.saveProject(project);
        User user = userService.findByUsername(principal.getName());
        List<User> userInOrder = userService.findByIds(project.getOrderedUsers());
        model.addAttribute("ordered", userInOrder);
        model.addAttribute("currentUser",user);
        model.addAttribute("project",project);
        model.addAttribute("tasks", project.getTasks());
        model.addAttribute("users",project.getUser());
        return "projectPage";
    }

    @PostMapping("/add/{id}")
    public String addToProject(@PathVariable("id") Long projectId, @RequestParam(name="userId") Long userId, Principal principal, Model model) {

        //todo check principal is author
        Project project = projectService.findById(projectId);
        User toAdd = userService.findByID(userId);
        projectService.addOrderedUser(project,toAdd);
        User user = userService.findByUsername(principal.getName());
        List<User> userInOrder = userService.findByIds(project.getOrderedUsers());
        model.addAttribute("ordered", userInOrder);
        model.addAttribute("currentUser",user);
        model.addAttribute("project",project);
        model.addAttribute("tasks", project.getTasks());
        model.addAttribute("users",project.getUser());
        return "projectPage";
    }

}

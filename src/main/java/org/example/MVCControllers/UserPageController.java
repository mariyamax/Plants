package org.example.MVCControllers;

import lombok.RequiredArgsConstructor;
import org.example.Enums.Area;
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
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class UserPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/user/{id}")
    private String getTestPage(@PathVariable("id") Long ID, Model model, Principal principal) {
        User owner = userService.findByID(ID);
        if (owner.getUsername().equals(principal.getName())) {
            model.addAttribute("admin", true);
        }
        model.addAttribute("user", owner);
        model.addAttribute("projects", owner.getProjects());
        model.addAttribute("project", new Project());
        return "userPage";
    }

    @PostMapping("/create")
    private String createPost(Principal principal, Model model, Project project, @RequestParam(name = "area", required = true) Area area) {
        User user = userService.findByUsername(principal.getName());
        projectService.createProject(user, project, area);
        model.addAttribute("user", user);
        model.addAttribute("projects", user.getProjects());
        model.addAttribute("project", new Project());
        return "userPage";
    }

    @PostMapping("/update")
    private String updateUser(Principal principal, String description, @RequestParam(name = "file", required = false) MultipartFile file, Model model) {
        User user = userService.findByUsername(principal.getName());
        if (file!=null) {
            userService.addImage(user, file);
        }
        user.setDescription(description);
        userService.saveUser(user);
        model.addAttribute("user", user);
        model.addAttribute("projects", user.getProjects());
        model.addAttribute("project", new Project());
        return "userPage";
    }

}

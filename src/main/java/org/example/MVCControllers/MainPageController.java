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
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainPageController {
    //todo change service

    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    @GetMapping("/projects")
    public String getStartPage(Principal principal, Model model){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("projects",projectService.findAll());
        model.addAttribute("user", user);
        return "mainPage";
    }

    @GetMapping("/filtered")
    public String getFilteredProject(@RequestParam(name = "area",required = false) Area area, @RequestParam(name = "area",required = false) String name,
                                     Model model, Principal principal){
        List<Project> projects = new ArrayList<>();
        if (area==null) {
            projects = projectService.filterByName(name);
        } else if (name ==null) {
            projects = projectService.filterByArea(area);
        } else {
            projects = projectService.filterByNameAndArea(name,area);
        }
        if(projects==null) {
            model.addAttribute("projects",projectService.findAll());
            model.addAttribute("error","Can not find projects by your filter");
        } else {
            model.addAttribute("projects", projects);
        }
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "mainPage";
    }

}

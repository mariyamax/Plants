package org.example.Controllers;

import lombok.AllArgsConstructor;
import org.example.Models.Users;
import org.example.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@AllArgsConstructor
public class SecurityViewController {

    @Autowired
    private UsersService service;


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new Users());
        return "registration.html";
    }

    @PostMapping("/registration")
    public String save(Users user, Model model) {
        user.setCoins(10);
        user.setRegular(1);
        user.setLastVisitTime(LocalDateTime.now());
        service.create(user);
        model.addAttribute("user", new Users());
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new Users());
        return "login.html";
    }

    @PostMapping("/login")
    public String mainPage(Users user, Model model) {
        Users fromDB = service.findByName(user.getUsername());
        service.updateCoins(fromDB);
        model.addAttribute("user", user);
        model.addAttribute("property", user.getPlants());
        return "userProfile";
    }

}

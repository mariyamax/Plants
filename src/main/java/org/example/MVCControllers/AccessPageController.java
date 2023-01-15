package org.example.MVCControllers;

import lombok.RequiredArgsConstructor;
import org.example.Enums.University;
import org.example.Models.User;
import org.example.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class AccessPageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getStartPage(){
        return "accessPage";
    }

    @GetMapping("/registration")
    public String getRegistrationForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("registration","registration");
        return "accessPage";
    }

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        model.addAttribute("login","login");
        return "accessPage";
    }

    @PostMapping("/registration")
    public String registerNewUser(User user, @RequestParam(name = "university",required = false) University university, Model model){
        if(university==null) {
            user.getUniversities().add(University.NONE);
        } else {
            user.getUniversities().add(university);
        }
        if (!userService.createUser(user)) {
            model.addAttribute("user", new User());
            model.addAttribute("registration","registration");
            model.addAttribute("error","can not create such user");
        } else {
            model.addAttribute("login","login");
        }
        return "accessPage";
    }

}

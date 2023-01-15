package org.example.MVCControllers;


import org.example.Models.ChatMessage;
import org.example.Models.Project;
import org.example.Models.User;
import org.example.Services.ChatMessageService;
import org.example.Services.ProjectService;
import org.example.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

import static org.example.Utils.RoomConverter.getRoom;

@Controller
public class MessagePageController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatMessageService messageService;


    @PostMapping("/messages")
    public String getMessagesPage(Principal principal,@RequestParam(name="name") String name, Model model) {
        System.out.println(principal.getName());
        System.out.println(name);
        User user = userService.findByUsername(principal.getName());
        Project project = projectService.findByName(name);
        if (project!=null) {
            if (project.getUser().contains(user)) {
                List<ChatMessage> messages = messageService.findByChatName(name);
                model.addAttribute("room", project.getName());
                model.addAttribute("user",user);
                model.addAttribute("messages",messages);
                return "messagePage";
            }
        } else {
            String customRoom = getRoom(principal.getName(),name,userService);
            List<ChatMessage> messages = messageService.findByChatName(name);
            model.addAttribute("room", customRoom);
            model.addAttribute("user",user);
            model.addAttribute("messages",messages);
            return "messagePage";
        }
        return "redirect:/";
    }

}


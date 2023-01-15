package org.example.Controllers;

import org.example.Models.ChatMessage;
import org.example.Repos.ChatMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
public class ChatController {

    @Autowired
    private ChatMessageRepo messageRepo;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;


    @MessageMapping("/chat/{room}")
    public void catchRoom(@DestinationVariable String room, @Payload ChatMessage chatMessage) {
        chatMessage.setDate(Timestamp.valueOf(LocalDateTime.now()));
        messageRepo.save(chatMessage);
        messagingTemplate.convertAndSend("/topic/".concat(room), chatMessage);
    }

}

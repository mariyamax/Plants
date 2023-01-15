package org.example.Services;

import org.example.Models.ChatMessage;
import org.example.Repos.ChatMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepo messageRepo;

    public List<ChatMessage> findByChatName(String name) {
        return messageRepo.findByChat(name);
    }
}

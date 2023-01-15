package org.example.Models;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity(name="messages")
public class ChatMessage {
    //todo will no massages in repo only rooms
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long ID;

    private MessageType type;
    private String content;
    private String sender;
    private Timestamp date;
    private String chat;


    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

}


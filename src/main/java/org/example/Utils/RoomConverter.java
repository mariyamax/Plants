package org.example.Utils;

import org.example.Models.User;
import org.example.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class RoomConverter {

    public static String getRoom(String firstUsername, String secondUsername, UserService service) {
        User first = service.findByUsername(firstUsername);
        User second = service.findByUsername(secondUsername);
        if (first.getID()<second.getID()) {
            return firstUsername+secondUsername;
        } else {
            return secondUsername+firstUsername;
        }
    }
}

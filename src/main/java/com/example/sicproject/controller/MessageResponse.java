package com.example.sicproject.controller;
import com.example.sicproject.model.Message;
import com.example.sicproject.model.User;

import java.util.List;

public class MessageResponse {

    private Message message;
    private List<User> seenBy;

    public MessageResponse(Message message, List<User> seenBy) {
        this.message = message;
        this.seenBy = seenBy;
    }

    // Getters and Setters
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<User> getSeenBy() {
        return seenBy;
    }

    public void setSeenBy(List<User> seenBy) {
        this.seenBy = seenBy;
    }

}


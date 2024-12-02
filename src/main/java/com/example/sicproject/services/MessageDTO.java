package com.example.sicproject.services;

import java.util.List;

public class MessageDTO {
    private long id;
    private String content;
    private String sender;
    private List<String> seenByUsers;

    public MessageDTO(long id, String content, String sender, List<String> seenByUsers) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.seenByUsers = seenByUsers;
    }

    // Getters and setters
}

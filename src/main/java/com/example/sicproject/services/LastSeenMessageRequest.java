package com.example.sicproject.services;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LastSeenMessageRequest {
    private Long lastSeenMessageId;

    // Optional: You can also add a constructor if you want
    public LastSeenMessageRequest() {
    }

    public LastSeenMessageRequest(Long lastSeenMessageId) {
        this.lastSeenMessageId = lastSeenMessageId;
    }
}

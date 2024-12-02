package com.example.sicproject.services;

import com.example.sicproject.model.Message;

import java.util.List;

public interface Messageservice {
    List<Message> findMessagesByRoomId(Long roomId);

    Message saveMessage(Message message);



}

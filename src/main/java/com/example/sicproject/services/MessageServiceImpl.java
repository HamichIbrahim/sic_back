package com.example.sicproject.services;


import com.example.sicproject.model.Message;
import com.example.sicproject.repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements Messageservice{

    private final MessageRepo messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepo messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> findMessagesByRoomId(Long roomId) {
        return messageRepository.findByRoomId(roomId);
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }


}

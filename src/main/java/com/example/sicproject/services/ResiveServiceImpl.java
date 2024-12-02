package com.example.sicproject.services;

import com.example.sicproject.model.*;
import com.example.sicproject.repository.MessageRepo;
import com.example.sicproject.repository.ResiveRepo;
import com.example.sicproject.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResiveServiceImpl implements Resiveservice {

    private final ResiveRepo resiveRepository;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    public ResiveServiceImpl(ResiveRepo resiveRepository) {
        this.resiveRepository = resiveRepository;
    }

    @Override
    public List<Message> getUnseenMessages(Long roomId, Long userId) {
        // Get all messages in the room
        List<Message> allMessages = messageRepo.findByRoomId(roomId);

        // Get all messages that the user has seen in the room
        List<Message> seenMessages = resiveRepository.findAllByUserIdAndMessageRoomId(userId, roomId)
                .stream()
                .map(Resive::getMessage)
                .collect(Collectors.toList());

        // Filter out the seen messages from all the messages
        return allMessages.stream()
                .filter(message -> !seenMessages.contains(message))
                .collect(Collectors.toList());
    }


    @Override
    public void markMessagesAsSeen(Long roomId, Long userId, List<Long> messageIds) {
        // Retrieve the user who will mark messages as seen
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Iterate through the list of message IDs
        for (Long messageId : messageIds) {
            // Retrieve the message by its ID
            Message message = messageRepo.findById(messageId)
                    .orElseThrow(() -> new RuntimeException("Message not found"));

            // Check if the Resive entry already exists
            ResiveId resiveId = new ResiveId(userId, messageId);
            boolean exists = resiveRepository.existsById(resiveId);

            if (!exists) {
                // Create and save the Resive relationship if it doesn't exist
                Resive resive = new Resive();
                resive.setId(resiveId);
                resive.setUser(user);
                resive.setMessage(message);
                resiveRepository.save(resive);
            }
        }
    }
    @Override
    public List<User> getUsersWhoViewedMessage(Long messageId, Long roomId,Long userId) {
        return resiveRepository.findUsersWhoViewedMessage(messageId, roomId);
    }

}
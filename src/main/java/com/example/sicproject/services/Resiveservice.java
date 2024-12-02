package com.example.sicproject.services;

import com.example.sicproject.model.Message;
import com.example.sicproject.model.User;

import java.util.List;

public interface Resiveservice {

     List<Message> getUnseenMessages(Long roomId, Long userId);

    void markMessagesAsSeen(Long roomId, Long userId, List<Long> messageIds);

    List<User> getUsersWhoViewedMessage(Long messageId, Long roomId,Long userId);
}
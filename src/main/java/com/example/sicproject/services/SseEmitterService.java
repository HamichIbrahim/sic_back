package com.example.sicproject.services;

import com.example.sicproject.model.Message;
import com.example.sicproject.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseEmitterService {

    private final Map<Long, List<SseEmitter>> emittersByRoom = new ConcurrentHashMap<>();

    public SseEmitter createEmitter(Long roomId) {
        SseEmitter emitter = new SseEmitter(0L);
        // Add the emitter to the list for the specified room
        emittersByRoom.computeIfAbsent(roomId, k -> new ArrayList<>()).add(emitter);

        // Remove the emitter when completed or timed out
        emitter.onCompletion(() -> emittersByRoom.get(roomId).remove(emitter));
        emitter.onTimeout(() -> emittersByRoom.get(roomId).remove(emitter));

        return emitter;
    }

    public void broadcastMessage(Message message) {
        List<SseEmitter> emitters = emittersByRoom.get(message.getRoom().getId());
        if (emitters != null) {
            Iterator<SseEmitter> iterator = emitters.iterator();
            while (iterator.hasNext()) {
                SseEmitter emitter = iterator.next();
                try {
                    // Log the message being sent
                    System.out.println("Sending message: " + message.getMessage());

                    // Convert the message to JSON
                    ObjectMapper objectMapper = new ObjectMapper();
                    String messageJson = objectMapper.writeValueAsString(message);
                    // Send the message
                    emitter.send(SseEmitter.event()
                            .data(message));

                    // Log success
                    System.out.println("Message sent successfully: " + messageJson);
                } catch (IOException e) {
                    // Log the exception and remove the emitter
                    System.err.println("Error sending message to emitter: " + e.getMessage());
                    iterator.remove(); // Safely remove the emitter
                }
            }
        }
    }

    public void broadcastMessageSeen(Long messageId, Long roomId, List<User> viewers) {
        List<SseEmitter> emitters = emittersByRoom.get(roomId);
        if (emitters != null) {
            Iterator<SseEmitter> iterator = emitters.iterator();
            while (iterator.hasNext()) {
                SseEmitter emitter = iterator.next();
                try {
                    // Log the viewer update being sent
                    System.out.println("Sending viewer update for message ID: " + messageId);

                    // Prepare the viewer data to be sent
                    ObjectMapper objectMapper = new ObjectMapper();
                    String viewerJson = objectMapper.writeValueAsString(viewers);

                    // Send viewer data as an SSE event
                    emitter.send(SseEmitter.event()
                            .data("{\"messageId\": " + messageId + ", \"viewers\": " + viewerJson + "}"));
                } catch (IOException e) {
                    // Handle failed delivery and remove emitter
                    System.err.println("Error sending viewer update: " + e.getMessage());
                    emitter.complete();
                    iterator.remove();
                }
            }
        }
    }



}

package com.example.sicproject.controller;


import com.example.sicproject.model.Message;
import com.example.sicproject.model.User;
import com.example.sicproject.repository.MessageRepo;
import com.example.sicproject.services.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/message")
public class MessageController {
   @Autowired
   private MessageRepo messageRepo;
    private final Messageservice messageService;
    private final Resiveservice resiveService;
    private final SseEmitterService sseEmitterService;

    @Autowired
    public MessageController(Messageservice messageService, Resiveservice resiveService,SseEmitterService sseEmitterService) {
        this.messageService = messageService;
        this.resiveService = resiveService;
        this.sseEmitterService=sseEmitterService;
    }

    @GetMapping("/{roomId}")
    public List<Message> getAllMessages(@PathVariable Long roomId) {
        return messageService.findMessagesByRoomId(roomId);
    }

    @GetMapping("/{roomId}/{userId}/unseenMessages")
    public List<Message> getUnseenMessages(@PathVariable Long roomId, @PathVariable Long userId) {
        return resiveService.getUnseenMessages(roomId, userId);
    }


    @PostMapping("/{roomId}/{userId}/markseen")
    @Transactional
    public ResponseEntity<Void> markMessagesSeen(
            @PathVariable Long roomId,
            @PathVariable Long userId,
            @RequestBody List<Long> messageIds) {

        // Log to debug message marking process
        System.out.println("Marking messages as seen for roomId: " + roomId + ", userId: " + userId);
        System.out.println("Messages to mark as seen: " + messageIds);

        // Mark messages as seen
        resiveService.markMessagesAsSeen(roomId, userId, messageIds);

        // Fetch and broadcast the updated viewer list for each message
        messageIds.forEach(messageId -> {
            List<User> viewers = resiveService.getUsersWhoViewedMessage(messageId, roomId, userId);

            // Log the viewers fetched for debugging
            System.out.println("Viewers for messageId " + messageId + ": " + viewers);


            sseEmitterService.broadcastMessageSeen(messageId, roomId, viewers);

        });

        return ResponseEntity.ok().build();
    }


    @PostMapping()
    public Message createMessage(@RequestBody Message msg) {
        if (msg.getMessage() == null || msg.getRoom() == null) {
            throw new IllegalArgumentException("Message content and room cannot be null");
        }
        msg.setDate(String.valueOf(LocalDateTime.now()));
        // Save the message to the database
        Message savedMessage = messageRepo.save(msg);
        // Notify all clients in the same room
        sseEmitterService.broadcastMessage(savedMessage);
        return savedMessage;
    }

    @GetMapping("/sse/room/{roomId}")
    public SseEmitter streamSseMvc(@PathVariable Long roomId) {
        return sseEmitterService.createEmitter(roomId);
    }


    @GetMapping("/{roomId}/{messageId}/{userId}/viewers")
    public List<User> getUsersWhoViewedMessage(
            @PathVariable Long roomId,
            @PathVariable Long messageId,
            @PathVariable Long userId
            ) {
        return resiveService.getUsersWhoViewedMessage(messageId, roomId,userId);

    }
}


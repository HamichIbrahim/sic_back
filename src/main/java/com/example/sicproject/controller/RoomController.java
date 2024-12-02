package com.example.sicproject.controller;


import com.example.sicproject.exception.ResourceNotFoundException;
import com.example.sicproject.model.ChatRoom;
import com.example.sicproject.model.Join;
import com.example.sicproject.model.User;
import com.example.sicproject.repository.JoinRepo;
import com.example.sicproject.repository.RoomRepo;
import com.example.sicproject.repository.UserRepo;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private JoinRepo joinRepo;

    @Autowired
    private UserRepo userRepo;
    @GetMapping
    public List<ChatRoom> getAllRooms(){
        return roomRepo.findAll();
    }

    // Create a new room
    @PostMapping("/create")
    public ResponseEntity<ChatRoom> createRoom(@RequestBody ChatRoom room) {
        System.out.println(room.getCreatedBy().getUsername());
        ChatRoom savedRoom = roomRepo.save(room);
        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }




    // Edit a room by ID
    @PutMapping("/{id}")
    public ResponseEntity<ChatRoom> updateRoom(@PathVariable Long id, @RequestBody ChatRoom roomDetails) {
        // Find the room by ID, throw exception if not found
        ChatRoom room = roomRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));

        // Update room fields with new data
        room.setLabel(roomDetails.getLabel());
        room.setPrivate(roomDetails.isPrivate());

        // Save the updated room
        ChatRoom updatedRoom = roomRepo.save(room);
        return ResponseEntity.ok(updatedRoom);
    }

    // Delete a room by ID
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        // Find the room by ID, throw exception if not found
        ChatRoom room = roomRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));

        // Delete all related join entries first
        joinRepo.deleteByRoomId(room.getId());

        // Now delete the room
        roomRepo.delete(room);

        return ResponseEntity.noContent().build(); // Return HTTP 204 No Content
    }
}

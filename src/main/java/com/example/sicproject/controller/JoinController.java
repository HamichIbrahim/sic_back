package com.example.sicproject.controller;


import com.example.sicproject.exception.ResourceNotFoundException;
import com.example.sicproject.model.ChatRoom;
import com.example.sicproject.model.Join;
import com.example.sicproject.model.JoinId;
import com.example.sicproject.model.User;
import com.example.sicproject.repository.JoinRepo;
import com.example.sicproject.repository.RoomRepo;
import com.example.sicproject.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/join")
public class JoinController {
    @Autowired
    private JoinRepo joinRepo;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoomRepo roomRepo;
    @PostMapping
    public ResponseEntity<Join> createOrUpdateJoin(@RequestBody JoinId joinId) {
        Optional<Join> existingJoin = joinRepo.findById(joinId);
        // Retrieve the room and user entities based on IDs
        User user = userRepo.findById(joinId.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        ChatRoom room = roomRepo.findById(joinId.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        Join join;
        if (existingJoin.isPresent()) {
            System.out.println("true");
            // If the join exists, update isActive to true
            join = existingJoin.get();
        } else {
            // If no join exists, create a new one
            join = new Join();
            join.setId(joinId);
            join.setUser(user);
            join.setRoom(room);
        }

        join.setActive(true); // Set isActive to true
        joinRepo.save(join);

        return ResponseEntity.ok(join);
    }
    @PutMapping("/{userId}/{roomId}")
    public ResponseEntity<Join> updateJoinStatus(@PathVariable Long userId, @PathVariable Long roomId) {
        // Logic to update the isActive status in the Join table
        JoinId joinId = new JoinId(userId, roomId); // Create composite key
        Join join = joinRepo.findById(joinId)
                .orElseThrow(() -> new RuntimeException("Join record not found"));

        join.setActive(false);
        joinRepo.save(join);

        return ResponseEntity.ok(join);
    }



}

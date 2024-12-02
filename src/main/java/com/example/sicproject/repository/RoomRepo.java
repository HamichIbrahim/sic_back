package com.example.sicproject.repository;

import com.example.sicproject.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<ChatRoom, Long> {

}

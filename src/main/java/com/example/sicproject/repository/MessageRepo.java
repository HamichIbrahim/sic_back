package com.example.sicproject.repository;

import com.example.sicproject.model.Message;
import com.example.sicproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findByRoomId(Long roomId);
    @Query("SELECT m, u.username " +
            "FROM Message m " +
            "LEFT JOIN Resive r ON r.message.id = m.id " +
            "LEFT JOIN User u ON r.user.id = u.id " +
            "WHERE m.room.id = :roomId")
    List<Object[]> findMessagesWithSeenUsersByRoomId(@Param("roomId") Long roomId);
}

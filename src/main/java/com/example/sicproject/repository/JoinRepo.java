package com.example.sicproject.repository;



import com.example.sicproject.model.Join;

import com.example.sicproject.model.JoinId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JoinRepo extends JpaRepository<Join, JoinId> {
    Optional<Join> findById(JoinId joinId); // Should already exist

    Optional<Join> findByUserIdAndRoomId(Long userId, Long roomId);
    void deleteByRoomId(Long roomId);
}
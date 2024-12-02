package com.example.sicproject.repository;

import com.example.sicproject.model.Resive;
import com.example.sicproject.model.ResiveId;
import com.example.sicproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResiveRepo extends JpaRepository<Resive, Long> {
    @Query("SELECT r.user FROM Resive r WHERE r.message.id = :messageId")
    List<User> findUsersByMessageId(Long messageId);

    List<Resive> findAllByUserIdAndMessageRoomId(Long userId, Long roomId);

    Optional<Resive> findById(ResiveId resiveId);

    boolean existsById(ResiveId resiveId);
    @Query("SELECT r.user FROM Resive r WHERE r.message.id = :messageId AND r.message.room.id = :roomId AND r.message.user.id != r.user.id")
    List<User> findUsersWhoViewedMessage(@Param("messageId") Long messageId, @Param("roomId") Long roomId);

}

package com.example.sicproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_Message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content", nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Creates a foreign key column for user_id
    private User user; // Reference to the User who sent the message



    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false) // Creates a foreign key column for user_id
    private ChatRoom room; // Reference to the User who sent the message

    @Column(name = "date", nullable = false)
    private String date; // Reference to the User who sent the message
}

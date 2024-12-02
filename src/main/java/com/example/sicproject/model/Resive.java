package com.example.sicproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "R_resive") // This table will store the relationships between User and Message
public class Resive {

    @EmbeddedId
    private ResiveId id; // Composite key (user_id and message_id)

    @ManyToOne
    @MapsId("userId") // Maps the userId from ResiveId to the User entity's "id"
    @JoinColumn(name = "user_id", referencedColumnName = "id") // "id" is the primary key in the User entity
    private User user;

    @ManyToOne
    @MapsId("messageId") // Maps the messageId from ResiveId to the Message entity's "id"
    @JoinColumn(name = "message_id", referencedColumnName = "id") // "id" is the primary key in the Message entity
    private Message message;
}

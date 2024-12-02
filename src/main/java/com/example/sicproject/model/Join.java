package com.example.sicproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "R_Join") // This table will store the relationships between User and ChatRoom
public class Join {

    @EmbeddedId
    private JoinId id; // Composite key (user_id and room_id)

    @ManyToOne
    @MapsId("userId") // Maps the userId from JoinId to the User entity's "id"
    @JoinColumn(name = "user_id", referencedColumnName = "id") // "id" is the primary key in the User entity
    private User user;

    @ManyToOne
    @MapsId("roomId") // Maps the roomId from JoinId to the ChatRoom entity's "id"
    @JoinColumn(name = "room_id", referencedColumnName = "id") // "id" is the primary key in the ChatRoom entity
    private ChatRoom room;

    @Column(name = "is_active", nullable = false)
    public boolean isActive = true; // default value is true
}

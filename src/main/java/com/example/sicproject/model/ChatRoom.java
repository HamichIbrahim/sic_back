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
@Table(name = "T_ChatRoom")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "label")
    private String label;

    @ManyToOne
    @JoinColumn(name = "user_id") // Foreign key column
    private User createdBy;

    @Column(name = "is_private", nullable = false)
    private boolean isPrivate = true; // default value is true

}

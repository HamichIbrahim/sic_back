package com.example.sicproject.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode // Required for proper functioning of composite keys
@Embeddable
public class ResiveId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "message_id")
    private Long messageId;
}

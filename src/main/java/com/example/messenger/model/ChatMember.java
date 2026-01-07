package com.example.messenger.model;

import com.example.messenger.enums.ChatRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_members", uniqueConstraints = @UniqueConstraint(columnNames = {"chat_id", "user_id"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private ChatRole role;

    private LocalDateTime joinedAt;

    @PrePersist
    public void prePersist() {
        this.joinedAt = LocalDateTime.now();
    }
}

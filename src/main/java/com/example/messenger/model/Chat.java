package com.example.messenger.model;

import com.example.messenger.enums.ChatRole;
import com.example.messenger.enums.ChatType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "chats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String avatarUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private User creator;
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;

    @OneToMany(
            mappedBy = "chat",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private Set<ChatMember> members = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private ChatType type;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastModified = LocalDateTime.now();
    }

    public void addMember(User user) {
        this.members.add(
                ChatMember.builder()
                        .chat(this)
                        .user(user)
                        .role(ChatRole.USER)
                        .build()
        );
    }
}

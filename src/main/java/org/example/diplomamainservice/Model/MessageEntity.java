package org.example.diplomamainservice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.diplomamainservice.Model.Enum.Role;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Entity
@Table(name  = "messages")
@EnableJpaAuditing
@AttributeOverride(name = "id", column = @Column(name = "message_id"))
public class MessageEntity extends BaseEntity {

    @NotNull
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private ChatEntity chat;

    @NotNull
    @Size(min = 1, max = 3000)
    private String message;

    @CreatedDate
    @Column(name = "created", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}

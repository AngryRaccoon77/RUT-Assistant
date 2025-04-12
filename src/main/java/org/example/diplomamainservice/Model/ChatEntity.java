package org.example.diplomamainservice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name  = "chats")
@EnableJpaAuditing
@AttributeOverride(name = "id", column = @Column(name = "chat_id"))
public class ChatEntity extends BaseEntity {

    @Size(max = 128)
    @NotNull
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "chat", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<MessageEntity> messages;

    @CreatedDate
    @Column(name = "created", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified")
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}

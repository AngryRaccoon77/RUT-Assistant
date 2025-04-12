package org.example.diplomamainservice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.misc.NotNull;
import org.example.diplomamainservice.Model.Enum.Role;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name  = "users")
@EnableJpaAuditing
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class UserEntity extends BaseEntity {

    @Size(max = 128)
    @NotNull
    @Column(name = "name", nullable = false, length = 128)
    private String username;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<ChatEntity> chats;

    @Size(max = 128)
    @NotNull
    @Column(name = "role", nullable = false, length = 128)
    private Role role;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

}

package com.pramod.auth.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection  = "users")
public class UserEntity {
        @Id
        private String id;
        private String username;
        private String email;
        private String passwordHash;
        private String accountCreatedAt;
        private String accountStatus;
}

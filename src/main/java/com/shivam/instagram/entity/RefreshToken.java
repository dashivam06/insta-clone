package com.shivam.instagram.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer tokenId;
    String token;
    Integer userId;
    String createdAt;
    String updatedAt;

    public RefreshToken(String token, Integer userId, String createdAt, String updatedAt) {
        this.token = token;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}

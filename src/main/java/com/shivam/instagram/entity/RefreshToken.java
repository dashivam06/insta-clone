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
public class RefreshToken 
{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Integer tokenId ;
    String token;
    boolean isValid ;
    
    Integer userId;

    String createdAt;
    String updatedAt ;

}

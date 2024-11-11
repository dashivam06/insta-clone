package com.shivam.instagram.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken 
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Integer tokenId ;
    String token;
    boolean isValid ;
    String deviceName;
    String ipAddress;

    String city ;
    String country ;

    Integer userId;

    String createdAt;
    String updatedAt ;



}

package com.shivam.instagram.entity;

import com.shivam.instagram.utils.Time;

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
    public RefreshToken(String token, boolean isValid, Integer userId, String createdAt, String updatedAt) {
        this.token = token;
        this.isValid = isValid;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

 public void setValid(boolean isValid) {
        this.isValid = isValid;
        String time = Time.getGMT_Time("yyyy-MM-dd HH:mm:ss");
        this.updatedAt = time;
    }
    
}

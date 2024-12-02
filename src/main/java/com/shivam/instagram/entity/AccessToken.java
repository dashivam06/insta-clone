package com.shivam.instagram.entity;

import org.springframework.beans.factory.annotation.Autowired;

import com.shivam.instagram.utils.Time;

import jakarta.persistence.Column;
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
public class AccessToken {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer tokenId;

    @Column(name = "token", length = 512)
    String token;
    boolean isValid;
    String deviceName;
    String ipAddress;

    String city;
    String country;

    Integer userId;

    String createdAt;

    String updatedAt;

    public AccessToken(String token, boolean isValid, String deviceName, String ipAddress, String city,
            String country, Integer userId, String currentGMT_Time) {

        this.token = token;
        this.isValid = isValid;
        this.deviceName = deviceName;
        this.ipAddress = ipAddress;
        this.city = city;
        this.country = country;
        this.userId = userId;
        this.createdAt = currentGMT_Time;
        this.updatedAt = currentGMT_Time;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
        String time = Time.getGMT_Time("yyyy-MM-dd HH:mm:ss");
        this.updatedAt = time;
    }

}

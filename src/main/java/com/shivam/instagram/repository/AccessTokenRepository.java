package com.shivam.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shivam.instagram.entity.AccessToken;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken,Integer>
{
    
}

package com.prod.Springboot.repository;

import com.prod.Springboot.entry.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User,String> {
    User findByusername(String username);
    User deleteByusername(String username);
}

package com.prod.Springboot.repository;

import com.prod.Springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;

public class UserRepoImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){
        Query query=new Query();
//        query.addCriteria(Criteria.where("username").is("Supriya"));
//      Here if any user opted for sentiment analysis and also provide the email, then list of that users should be returned
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true).
                and("mail").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
        List<User> users= mongoTemplate.find(query,User.class);
        return users;
    }
}

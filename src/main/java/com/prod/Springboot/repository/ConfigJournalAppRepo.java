package com.prod.Springboot.repository;

import com.prod.Springboot.entity.ConfigJournalAppEntity;
import com.prod.Springboot.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepo extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}

package com.prod.Springboot.repository;

import com.prod.Springboot.entity.journalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface journalEntryRepo extends MongoRepository<journalEntry, ObjectId> {
}

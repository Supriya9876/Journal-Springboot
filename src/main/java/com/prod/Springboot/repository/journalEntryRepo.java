package com.prod.Springboot.repository;

import com.prod.Springboot.entry.journalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface journalEntryRepo extends MongoRepository<journalEntry, ObjectId> {
}

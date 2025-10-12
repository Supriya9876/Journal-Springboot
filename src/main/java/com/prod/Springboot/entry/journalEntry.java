package com.prod.Springboot.entry;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document
@Getter
@Setter
public class journalEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String desc;
    private LocalDateTime date;

}

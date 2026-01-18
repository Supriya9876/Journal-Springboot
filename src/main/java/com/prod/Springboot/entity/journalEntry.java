package com.prod.Springboot.entity;

import com.prod.Springboot.enums.Sentiment;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@Setter
@NoArgsConstructor
@Data
public class journalEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String desc;
    private LocalDateTime date;
    private Sentiment sentiment;
}

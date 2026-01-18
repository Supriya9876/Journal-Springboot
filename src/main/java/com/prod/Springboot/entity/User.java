package com.prod.Springboot.entity;



import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
//import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@ComponentScan
@Document(collection = "users")  //It will tell the spring that the class is mapped
// with the mongodb means the object of the class will
// be the document in mongodb
@Data
@NoArgsConstructor
public class User {
    @Id  //Spring will inject some value if we don't give the value by own
    private ObjectId id;
    @Indexed(unique = true)  //Everyone's username should be unique
    @NonNull
    private String username;
    @NonNull
    private String password;
    private String mail;
    private boolean sentimentAnalysis;
    @DBRef
    private ArrayList<journalEntry> journalEntries=new ArrayList<>();

    //    User entities to represent user data model more precisely the role of the users
    private List<String> roles;

    public List<String> getRoles() {
        return roles != null ? roles : new ArrayList<>();
    }



}

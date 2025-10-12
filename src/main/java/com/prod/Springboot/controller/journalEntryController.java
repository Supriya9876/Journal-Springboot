package com.prod.Springboot.controller;


import com.prod.Springboot.entry.User;
import com.prod.Springboot.entry.journalEntry;
import com.prod.Springboot.service.UserServices;
import com.prod.Springboot.service.journalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class journalEntryController {

    @Autowired
    private journalEntryService JournalEntryService;

    @Autowired
    private UserServices userServices;


    @GetMapping("/get")
    public ResponseEntity<?> getAllValues(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String uName= authentication.getName();
        User currUser=userServices.findByUserName(uName);
        List<journalEntry> jE=currUser.getJournalEntries();
        if(jE!=null && !jE.isEmpty()){
            return new ResponseEntity<>(jE, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{myid}")
    public ResponseEntity<journalEntry> getById(@PathVariable ObjectId myid){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String uName= authentication.getName();
        User currUser=userServices.findByUserName(uName);
        List<journalEntry> collect= currUser.getJournalEntries().stream().filter(x-> x.getId().equals(myid)).toList();
        if(!collect.isEmpty()){
            Optional<journalEntry> myEntry=JournalEntryService.getById(myid);
            if(!myEntry.isEmpty()){
                 return new ResponseEntity<>(myEntry.get(),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<journalEntry> addValues(@RequestBody journalEntry jE){
        try{
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String uName= authentication.getName();
            JournalEntryService.saveEntriesByUser(jE,uName);
            return new ResponseEntity<>( HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping
    public ResponseEntity<?> getJEByName(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String uName= authentication.getName();
        User user= userServices.findByUserName(uName);
        List<journalEntry> journalList=user.getJournalEntries();
        if(!journalList.isEmpty()){
            return new ResponseEntity<>(journalList,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{journalId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId journalId){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String uName= authentication.getName();
        boolean ans=JournalEntryService.deleteById(journalId,uName );
        if(ans){ return new ResponseEntity<>( HttpStatus.NO_CONTENT);}
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{journalId}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId journalId, @RequestBody journalEntry jEntry){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String uName= authentication.getName();
        User user= userServices.findByUserName(uName);
        Optional<journalEntry> oldEntry= JournalEntryService.getById(journalId);
        if(oldEntry!=null){
            journalEntry old=oldEntry.get();
            old.setTitle(!jEntry.getTitle().isEmpty() ? jEntry.getTitle() : old.getTitle());
            old.setDesc(jEntry.getDesc()!=null && !jEntry.getDesc().isEmpty() ? jEntry.getDesc() : old.getDesc());
            JournalEntryService.save(old);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

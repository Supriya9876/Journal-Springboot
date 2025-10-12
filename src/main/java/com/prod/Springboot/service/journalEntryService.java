package com.prod.Springboot.service;

import com.prod.Springboot.entry.User;
import com.prod.Springboot.entry.journalEntry;
import com.prod.Springboot.repository.journalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class journalEntryService {
    @Autowired
    private journalEntryRepo JournalEntryRepo;

    @Autowired
    private UserServices uServices;

    @Transactional
    public void saveEntriesByUser(journalEntry JEntry, String userName){
        User u= uServices.findByUserName(userName);
        JEntry.setDate(LocalDateTime.now());
        journalEntry saved= JournalEntryRepo.save(JEntry);
        u.getJournalEntries().add(saved);
        uServices.saveUser(u);
    }
    public void save(journalEntry JEntry){
        journalEntry saved= JournalEntryRepo.save(JEntry);
    }

    public List<journalEntry> getAll(){
        return JournalEntryRepo.findAll();
    }

    public Optional<journalEntry> getById(ObjectId id){
        return JournalEntryRepo.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String user){
        boolean removed=false;
        try{
            User usr= uServices.findByUserName(user);
            removed= usr.getJournalEntries().removeIf(x ->x.getId().equals(id));
            if(removed){
                uServices.saveUser(usr);
                JournalEntryRepo.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("An Error occoured while deleting: " ,e);
        }
        return removed;

    }


}

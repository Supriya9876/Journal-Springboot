package com.prod.Springboot.cache;


import com.prod.Springboot.entity.ConfigJournalAppEntity;
import com.prod.Springboot.repository.ConfigJournalAppRepo;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
@Setter
public class AppCache {

    public enum keys{
        weather_api;
    }

    @Autowired
    private ConfigJournalAppRepo configJournalAppRepo;


    public Map<String,String> appCache ;

    @PostConstruct
    public void init(){
        appCache=new HashMap<>();
        List<ConfigJournalAppEntity> all=configJournalAppRepo.findAll();
        for(ConfigJournalAppEntity c:all){
            appCache.put(c.getKey(),c.getValue());
        }
    }
}

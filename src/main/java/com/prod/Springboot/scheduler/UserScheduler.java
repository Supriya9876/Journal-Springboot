package com.prod.Springboot.scheduler;


import com.prod.Springboot.entity.User;
import com.prod.Springboot.entity.journalEntry;
import com.prod.Springboot.enums.Sentiment;
import com.prod.Springboot.model.SentimentData;
import com.prod.Springboot.repository.UserRepoImpl;
import com.prod.Springboot.service.EmailService;
import com.prod.Springboot.service.SentimentAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepoImpl userRepository;


    @Autowired
    private SentimentAnalysis sentimentAnalysis;


    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Scheduled(cron = "*/10 * * * * *")
    public void fetchUsersAndSendMail(){
        List<User> users= userRepository.getUserForSA();
        for(User u: users){
            List<journalEntry> journalEntries=u.getJournalEntries();
            List<Sentiment> sentiments= journalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(300, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentsCount= new HashMap<>();
            for(Sentiment sentiment: sentiments){
                if(sentiment!=null){
                    sentimentsCount.put(sentiment,sentimentsCount.getOrDefault(sentiment,0)+1);
                }
            }
            Sentiment mostFrequentSentiment=null;
            int maxCount=0;
            for(Map.Entry<Sentiment,Integer> entry:sentimentsCount.entrySet()){
                if(entry.getValue()>maxCount){
                    maxCount= entry.getValue();
                    mostFrequentSentiment=entry.getKey();
                }
            }
//            log.info("User: {} | Most frequent sentiment (last 7 days): {}",
//                    u.getMail(), mostFrequentSentiment);

//            if(mostFrequentSentiment!=null){
//                SentimentData sentimentData= SentimentData.builder().email(u.getMail()).sentiment("Sentiment for last 7 days"+ mostFrequentSentiment).build();
//                kafkaTemplate.send("weekly_sentiments", sentimentData.getEmail(), sentimentData);
//            }
//
            if (mostFrequentSentiment != null && u.getMail() != null && !u.getMail().isBlank()) {

                SentimentData sentimentData = SentimentData.builder()
                        .email(u.getMail())
                        .sentiment("Sentiment for last 7 days " + mostFrequentSentiment)
                        .build();

                kafkaTemplate.send("weekly_sentiments", sentimentData.getEmail(), sentimentData);
            }
//            log.info("About to send test message");
//
//            String json =
//                    "{\"email\":\"test@example.com\",\"sentiment\":\"TEST_MESSAGE\"}";
//
//            kafkaTemplate.send("weekly_sentiments", "test-key", json);
//            kafkaTemplate.flush();
//
//            log.info("Message sent successfully");





        }
    }
}

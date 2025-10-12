//package com.prod.Springboot.service;
//
//import com.prod.Springboot.repository.UserRepo;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class UserServiceTest {
//
//    @Autowired
//    private UserRepo uRepo;
//
//    @Test
//    public void checkUser(){
//        assertEquals(4, 2+2);
//        assertNotNull(uRepo.findByusername("Rohit"));
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "1,1,2",
//            "3,4,7",
//            "6,6,12"
//    })
//    public void test(int a , int b , int expected){
//        assertEquals(expected, a + b);
//    }
//}

package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void redisSet(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("age", 41);
    }

    @Test
    void redisGet() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        System.out.println(valueOperations.get("age"));
    }
}

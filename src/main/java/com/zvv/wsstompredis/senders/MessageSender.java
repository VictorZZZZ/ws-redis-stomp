package com.zvv.wsstompredis.senders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@EnableScheduling // Turns the timer function
@Component
public class MessageSender {
    @Autowired
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    @Scheduled(fixedRate = 5000)
    // The interval is issued to the Redis Message Queue channel via the StringRedistemplate object
    public void sendTopicMessage() {
        stringRedisTemplate.convertAndSend("Mytopic", "Mytopic: Time " + new Date());
    }
}

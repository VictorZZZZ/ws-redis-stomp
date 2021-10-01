package com.zvv.wsstompredis.listener;

import com.zvv.wsstompredis.services.SendMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TopicListener implements MessageListener {

    private final SendMessageService sendMessageService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("I am Topic monitor " + message.toString());
        sendMessageService.send(message.toString());
    }
}

package com.zvv.wsstompredis.services;

import com.zvv.wsstompredis.entities.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendMessageService {
    @Autowired
    private SimpMessagingTemplate template;

    public void send(String message){
        ChatMessage chatMessage=new ChatMessage();
        chatMessage.setContent(message);
        chatMessage.setType(ChatMessage.MessageType.CHAT);
        chatMessage.setSender("Listener Broadcast News");
        System.out.println(chatMessage);
        template.convertAndSend("/topic/public",chatMessage);
    }
}

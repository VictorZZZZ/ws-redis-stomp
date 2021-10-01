package com.zvv.wsstompredis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private static long HEART_BEAT = 10000;

//    @Bean
//    public ServerEndpointExporter serverEndpointExporter(){
//        return new ServerEndpointExporter();
//    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                //.setAllowedOrigins("*")
                .setAllowedOriginPatterns("?token=*")
                .addInterceptors()
//                .addInterceptors(new SessionAuthHandshakeInterceptor())
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Set up a simple message agent, it uses Memory as a message proxy,
        // wherein / user and / topic is the data prefix we sent to the front desk. The front end must subscribe to messages starting with / user (.Subscribe () to listen).
        // setHeartBeatValue Set the heartbeat sent by the background to the front desk,
        // Note: setHeartBeatvalue This cannot be set separately, otherwise don't work, you have to work with the settaskscheduler to take effect.

        ThreadPoolTaskScheduler te = new ThreadPoolTaskScheduler();
        te.setPoolSize(1);
        te.setThreadNamePrefix("wss-heartbeat-thread-");
        te.initialize();
        registry.enableSimpleBroker("/user", "/topic").setHeartbeatValue(new long[]{HEART_BEAT, HEART_BEAT}).setTaskScheduler(te);

        registry.setApplicationDestinationPrefixes("/app");

    }


    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(500 * 1024 * 1024);
        registration.setSendBufferSizeLimit(1024 * 1024 * 1024);
        registration.setSendTimeLimit(200000);
    }
}

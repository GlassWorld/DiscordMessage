package com.glass.discordbot.service;

import com.glass.discordbot.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MsgService {

    @Value("${discord.webhook.alert.url}")
    String webhookUrl;

    public boolean sendMsg(String msg){

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "application/json; utf-8");
            HttpEntity<Message> messageEntity = new HttpEntity<>(new Message(msg), httpHeaders);

            System.out.println(webhookUrl);

            RestTemplate template = new RestTemplate();
            ResponseEntity<String> response = template.exchange(
                    "https://discord.com/api/webhooks/"+webhookUrl,
                    HttpMethod.POST,
                    messageEntity,
                    String.class
            );


            // response에 대한 처리
            if(response.getStatusCode().value() != HttpStatus.NO_CONTENT.value()){
                System.out.println("메시지 전송 이후 에러 발생");
                return false;
            }

        } catch (Exception e) {
            System.out.println("에러 발생 :: " + e);
            return false;
        }

        return true;
    }
}

/* 
package com.edu.consumerService.consumers;

import java.io.IOException;

import com.edu.consumerService.dto.EmailDTO;
import com.edu.consumerService.services.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

//Essa parte vai para outro mciroservice do aluno consumer
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AlunoConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.queueAluno}")
    public void consumer(@Payload EmailDTO emailDTO) {
        emailService.sendEmail(emailDTO);
        System.out.println("Email Status: SENT");
    }

    @RabbitListener(queues = "${spring.rabbitmq.queueAluno}")
    public void consumer(Message message) {
        try {
            EmailDTO emailDTO = new ObjectMapper().readValue(message.getBody(), EmailDTO.class);
            emailService.sendEmail(emailDTO);
            System.out.println("Email Status: SENT");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

*/
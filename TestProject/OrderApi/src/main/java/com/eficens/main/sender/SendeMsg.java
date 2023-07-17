package com.eficens.main.sender;

import com.eficens.main.entity.EmailOrderDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendeMsg {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(EmailOrderDetails emailOrderDetails) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(emailOrderDetails);
        jmsTemplate.convertAndSend("new-order-notification",message);
    }
}


package com.german.phoneemulator.controllers;


import com.german.phoneemulator.documents.Message;
import com.german.phoneemulator.dto.MessageDto;
import com.german.phoneemulator.exceptions.MessageSendingException;
import com.german.phoneemulator.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {


    private final MessageService messageService;


    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @GetMapping("{to}")
    public List<Message> messagesGet(@PathVariable String to) {
        List<Message> messages = this.messageService.retrieve(to);

        return messages;
    }


    @PostMapping
    public String messagesSendPost(MessageDto messageDto) throws MessageSendingException {
        String responseString = this.messageService.createAndSend(messageDto);

        return responseString;

    }

}

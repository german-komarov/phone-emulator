package com.german.phoneemulator.services;

import com.german.phoneemulator.documents.Message;
import com.german.phoneemulator.dto.MessageDto;
import com.german.phoneemulator.exceptions.MessageSendingException;
import com.german.phoneemulator.repositories.MessageRepository;
import com.german.phoneemulator.util.constants.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;


    @Autowired
    public MessageService(MessageRepository messageRepository, SimpMessagingTemplate simpMessagingTemplate) {
        this.messageRepository = messageRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    public List<Message> retrieve(String to) {
        List<Message> messages = this.messageRepository.findByToOrderByReceivedAtDesc(to);

        return messages;
    }


    public Message save(Message message) {
        message = this.messageRepository.save(message);

        return message;
    }


    public String createAndSend(MessageDto messageDto) throws MessageSendingException {

        String from = messageDto.getFrom();
        String to = messageDto.getTo();
        String text = messageDto.getText();

        from = from.trim();
        to = to.trim();

        try {
            Long.parseLong(from);
            Long.parseLong(to);
        } catch (NumberFormatException e) {
            throw new MessageSendingException("Phone number can contain only digits");
        }

        if (from.length() < 7 || to.length() < 7 || from.length() > 15 || to.length() > 15) {
            throw new MessageSendingException("Trimmed from phone number must contain from 7 to 15 digits");
        }


        if (text.length() > 500) {
            throw new MessageSendingException("Text can contain maximum 500 characters");
        }


        Message message = new Message();
        message.setFrom(from);
        message.setTo(to);
        message.setText(text);
        message.setReceivedAt(LocalDateTime.now());

        message = this.save(message);

        this.send(message);

        return Responses.MESSAGE_SAVED_AND_SENT.name();
    }


    public void send(Message message) throws MessageSendingException {
        try {
            simpMessagingTemplate.convertAndSend(String.format("/topic/messages/%s", message.getTo()), message);
        } catch (MessagingException e) {
            throw new MessageSendingException("Something went wrong in the server during sending your message, try later");
        }
    }


}

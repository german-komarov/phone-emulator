package com.german.phoneemulator.repositories;

import com.german.phoneemulator.documents.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    List<Message> findByToOrderByReceivedAtDesc(String to);

}

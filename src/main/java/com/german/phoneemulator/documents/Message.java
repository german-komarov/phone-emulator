package com.german.phoneemulator.documents;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document(collection = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Message {

    @MongoId
    private String id;

    private String from;

    private String to;

    private String text;

    private LocalDateTime receivedAt;


}

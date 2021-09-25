package com.german.phoneemulator.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MessageDto {

    @NotBlank(message = "Sender phone number cannot be blank")
    @Size(min = 7, max = 15, message = "Phone number must consist from 10 to 30 digits")
    private String from;
    @NotBlank(message = "Receiver phone number cannot be blank")
    @Size(min = 7, max = 15, message = "Phone number must consist from 10 to 30 digits")
    private String to;
    @Size(max = 500, message = "Message can consist maximum 500 characters")
    private String text;
}

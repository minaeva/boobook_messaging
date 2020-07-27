package ua.kiev.minaeva.boobookmessagingservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {

    private Long id;
    private Long from;
    private Long to;
    private String message;
    private LocalDateTime dateTime;

}

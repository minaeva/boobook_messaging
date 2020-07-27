package ua.kiev.minaeva.boobookmessagingservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import ua.kiev.minaeva.boobookmessagingservice.dto.MessageDto;
import ua.kiev.minaeva.boobookmessagingservice.exception.BoobookNotFoundException;
import ua.kiev.minaeva.boobookmessagingservice.exception.BoobookValidationException;
import ua.kiev.minaeva.boobookmessagingservice.service.MessageService;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@RestController
@Log
@RequestMapping("/messages")
@RequiredArgsConstructor
@CrossOrigin
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final int BEARER_LETTERS_QUANTITY = 7;

    @GetMapping
    public List<MessageDto> getAllMessages(@RequestHeader(AUTHORIZATION) String jwtWithBearer) throws BoobookValidationException {
        log.info("handling GET ALL MESSAGES request");
        String jwt = getJwtFromString(jwtWithBearer);

        return messageService.getAllMessages(jwt);
    }

    @MessageMapping("/chat/{to}")
    public void sendMessage(@RequestHeader(AUTHORIZATION) String jwtWithBearer, @DestinationVariable Long to, MessageDto messageDto) throws BoobookValidationException {
        log.info("handling SEND MESSAGE: " + messageDto + " to: " + to);
        String jwt = getJwtFromString(jwtWithBearer);

        messageDto.setTo(to);
        messageService.saveMessage(jwt, messageDto);
        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, messageDto);
    }

    @GetMapping("/user")
    public List<MessageDto> getUserMessages(@RequestHeader(AUTHORIZATION) String jwtWithBearer) throws BoobookValidationException, BoobookNotFoundException {
        log.info("handling GET USER MESSAGES request");
        String jwt = getJwtFromString(jwtWithBearer);

        return messageService.getUserMessages(jwt);
    }

    private String getJwtFromString(String bearer) throws BoobookValidationException {
        if (hasText(bearer) && bearer.startsWith(BEARER)) {
            return bearer.substring(BEARER_LETTERS_QUANTITY);
        } else {
            throw new BoobookValidationException("Token is not valid");
        }
    }

}

package ua.kiev.minaeva.boobookmessagingservice.service;

import ua.kiev.minaeva.boobookmessagingservice.dto.MessageDto;
import ua.kiev.minaeva.boobookmessagingservice.exception.BoobookNotFoundException;
import ua.kiev.minaeva.boobookmessagingservice.exception.BoobookValidationException;

import java.util.List;

public interface MessageService {

    List<MessageDto> getAllMessages(String jwt) throws BoobookValidationException;

    List<MessageDto> getUserMessages(String jwt) throws BoobookValidationException, BoobookNotFoundException;

    MessageDto saveMessage(String jwt, MessageDto messageDto) throws BoobookValidationException;
}

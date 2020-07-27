package ua.kiev.minaeva.boobookmessagingservice.service;

import org.springframework.util.StringUtils;
import ua.kiev.minaeva.boobookmessagingservice.dto.MessageDto;
import ua.kiev.minaeva.boobookmessagingservice.exception.BoobookValidationException;

public class MessageHelper {

    private MessageHelper() {
    }

    public static void validateMessageDto(MessageDto messageDto) throws BoobookValidationException {
        if (StringUtils.isEmpty(messageDto.getFrom())) {
            throw new BoobookValidationException("From cannot be empty");
        }
        if (StringUtils.isEmpty(messageDto.getMessage())) {
            throw new BoobookValidationException("Message cannot be empty");
        }
        if (StringUtils.isEmpty(messageDto.getTo())) {
            throw new BoobookValidationException("To cannot be empty");
        }
    }

}

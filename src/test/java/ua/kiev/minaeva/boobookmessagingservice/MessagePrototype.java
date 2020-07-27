package ua.kiev.minaeva.boobookmessagingservice;

import org.mapstruct.factory.Mappers;
import ua.kiev.minaeva.boobookmessagingservice.dto.MessageDto;
import ua.kiev.minaeva.boobookmessagingservice.entity.Message;
import ua.kiev.minaeva.boobookmessagingservice.mapper.MessageMapper;

public class MessagePrototype {

    private static MessageMapper mapper = Mappers.getMapper(MessageMapper.class);

    public static Message aMessage() {
        Message message = new Message();
        message.setFrom(1L);
        message.setTo(2L);
        message.setMessage("Nick is so salty since his parents took away his car!");
        return message;
    }

    public static MessageDto aMessageDto() {
        return mapper.messageToDto(aMessage());
    }
}

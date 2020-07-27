package ua.kiev.minaeva.boobookmessagingservice.mapper;

import org.mapstruct.Mapper;
import ua.kiev.minaeva.boobookmessagingservice.dto.MessageDto;
import ua.kiev.minaeva.boobookmessagingservice.entity.Message;

@Mapper
public interface MessageMapper {

    MessageDto messageToDto(Message message);

    Message dtoToMessage(MessageDto messageDto);
}

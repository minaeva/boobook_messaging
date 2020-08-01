package ua.kiev.minaeva.boobookmessagingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.kiev.minaeva.boobookmessagingservice.dto.MessageDto;
import ua.kiev.minaeva.boobookmessagingservice.entity.Message;

@Mapper
public interface MessageMapper {

    @Mapping(target = "dateTimeString", source = "message.dateTime", dateFormat = "dd MMM yyyy, HH:mm")
    MessageDto messageToDto(Message message);

    Message dtoToMessage(MessageDto messageDto);
}

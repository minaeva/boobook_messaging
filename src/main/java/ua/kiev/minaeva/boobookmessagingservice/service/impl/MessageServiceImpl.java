package ua.kiev.minaeva.boobookmessagingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ua.kiev.minaeva.boobookmessagingservice.client.UserService;
import ua.kiev.minaeva.boobookmessagingservice.dto.MessageDto;
import ua.kiev.minaeva.boobookmessagingservice.dto.ReaderDto;
import ua.kiev.minaeva.boobookmessagingservice.entity.Message;
import ua.kiev.minaeva.boobookmessagingservice.exception.BoobookNotFoundException;
import ua.kiev.minaeva.boobookmessagingservice.exception.BoobookValidationException;
import ua.kiev.minaeva.boobookmessagingservice.mapper.MessageMapper;
import ua.kiev.minaeva.boobookmessagingservice.repository.MessageRepository;
import ua.kiev.minaeva.boobookmessagingservice.service.MessageService;

import java.util.List;
import java.util.stream.Collectors;

import static ua.kiev.minaeva.boobookmessagingservice.service.MessageHelper.validateMessageDto;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;

    private MessageMapper mapper = Mappers.getMapper(MessageMapper.class);

    public List<MessageDto> getAllMessages(String jwt) throws BoobookValidationException {
        ReaderDto readerRequesting = userService.getUserByJwt(jwt);

        return messageRepository.findAll().stream()
                .map(message -> mapper.messageToDto(message))
                .collect(Collectors.toList());
    }

    public List<MessageDto> getUserMessages(String jwt) throws BoobookValidationException, BoobookNotFoundException {
        ReaderDto readerRequesting = userService.getUserByJwt(jwt);

        Long readerId = readerRequesting.getId();
        List<Message> readerMessages = messageRepository.findByFromOrTo(readerId, readerId)
                .orElseThrow(() -> new BoobookNotFoundException("Not any message own be reader with id " + readerId));

        return readerMessages.stream()
                .map(message -> mapper.messageToDto(message))
                .collect(Collectors.toList());
    }

    public MessageDto saveMessage(String jwt, MessageDto messageDto) throws BoobookValidationException {
        ReaderDto readerRequesting = userService.getUserByJwt(jwt);

        validateMessageDto(messageDto);

        Message newMessage = mapper.dtoToMessage(messageDto);
        return mapper.messageToDto(messageRepository.save(newMessage));
    }

}

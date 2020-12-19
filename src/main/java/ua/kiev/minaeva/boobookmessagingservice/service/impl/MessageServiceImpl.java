package ua.kiev.minaeva.boobookmessagingservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
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

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ua.kiev.minaeva.boobookmessagingservice.service.MessageHelper.validateMessageDto;

@Service
@RequiredArgsConstructor
@Log
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

    public List<MessageDto> getOwnMessages(String jwt) throws BoobookValidationException {
        Long currentReaderId = userService.getUserByJwt(jwt).getId();

        List<Message> readerMessages = messageRepository.findByFromOrToOrderByDateTimeAsc(currentReaderId, currentReaderId)
                .orElseGet(LinkedList::new);

        return readerMessages.stream()
                .map(message -> mapper.messageToDto(message))
                .collect(Collectors.toList());
    }

    public List<MessageDto> getMessagesWithUser(String jwt, Long readerId) throws BoobookValidationException, BoobookNotFoundException {
        List<MessageDto> allMessagesOfCurrentUser = getOwnMessages(jwt);

        return allMessagesOfCurrentUser.stream()
                .filter(messageDto ->
                        messageDto.getFrom() == readerId || (messageDto.getTo() == readerId))
                .collect(Collectors.toList());
    }

    public List<ReaderDto> getConversationalists(String jwt) throws BoobookValidationException, BoobookNotFoundException {
        Long currentReaderId = userService.getUserByJwt(jwt).getId();
        List<Message> readerMessages = messageRepository.findByFromOrToOrderByDateTimeDesc(currentReaderId, currentReaderId)
                .orElseGet(LinkedList::new);

        Set<Long> chronologicalIds = readerMessages.stream()
                .map(message -> {
                    /* find the reader currentReader had a chat with */
                    if (message.getFrom() == currentReaderId) {
                        return message.getTo();
                    } else
                        return message.getFrom();
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));

        List<ReaderDto> conversationalists = new LinkedList<>();
        for (Long id : chronologicalIds) {
            try {
                ReaderDto readerDto = userService.getUserByID(jwt, id);
                conversationalists.add(readerDto);
            } catch (BoobookNotFoundException e) {
                log.warning("User with id " + id + " not found: " + e.getMessage());
            }
        }

        return conversationalists;
    }

    public MessageDto saveMessage(String jwt, MessageDto messageDto) throws BoobookValidationException {
        ReaderDto readerRequesting = userService.getUserByJwt(jwt);

        validateMessageDto(messageDto);

        Message newMessage = mapper.dtoToMessage(messageDto);
        return mapper.messageToDto(messageRepository.save(newMessage));
    }

}

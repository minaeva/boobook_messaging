package ua.kiev.minaeva.boobookmessagingservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.kiev.minaeva.boobookmessagingservice.controller.ControllerAdvisor;
import ua.kiev.minaeva.boobookmessagingservice.controller.MessageController;
import ua.kiev.minaeva.boobookmessagingservice.service.MessageService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.kiev.minaeva.boobookmessagingservice.MessagePrototype.aMessageDto;
import static ua.kiev.minaeva.boobookmessagingservice.controller.MessageController.AUTHORIZATION;

public class MessageControllerTest {

    MockMvc mockMvc;
    ObjectMapper objectMapper;
    MessageService messageService;
    SimpMessagingTemplate simpMessagingTemplate;

    @BeforeEach
    void setUp() {
        messageService = mock(MessageService.class);
        simpMessagingTemplate = mock(SimpMessagingTemplate.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new MessageController(simpMessagingTemplate, messageService))
                .setControllerAdvice(new ControllerAdvisor())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllMessages() throws Exception {
        when(messageService.getAllMessages(anyString())).thenReturn(Collections.singletonList(aMessageDto()));
        mockMvc.perform(get("/messages")
                .header(AUTHORIZATION, "Bearer 123"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(aMessageDto()))));
    }

    @Test
    void getAllMessages_nonValidToken() throws Exception {
        mockMvc.perform(get("/messages")
                .header(AUTHORIZATION, "123"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void getUserMessages() throws Exception {
        when(messageService.getUserMessages(anyString())).thenReturn(Collections.singletonList(aMessageDto()));
        mockMvc.perform(get("/messages/user")
                .header(AUTHORIZATION, "Bearer 123"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(aMessageDto()))));
    }

    @Test
    void getUserMessages_nonValidToken() throws Exception {
        mockMvc.perform(get("/messages/user")
                .header(AUTHORIZATION, "123"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}

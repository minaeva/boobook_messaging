package ua.kiev.minaeva.boobookmessagingservice.client;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kiev.minaeva.boobookmessagingservice.dto.ReaderDto;
import ua.kiev.minaeva.boobookmessagingservice.exception.BoobookNotFoundException;
import ua.kiev.minaeva.boobookmessagingservice.exception.BoobookValidationException;

@Service
@Log
public class UserService {

    @Autowired
    private UserClient userClient;

    public ReaderDto getUserByJwt(String jwt) throws BoobookValidationException {
        log.info("MessageService: handling GET USER BY JWT request, jwt is: " + jwt);
        return userClient.getUserByJwt(jwt);
    }

    public ReaderDto getUserByID(String jwt, Long id) throws BoobookNotFoundException {
        log.info("MessageService: handling GET USER BY ID request, jwt is: " + jwt + ", id is: " + id);
        return userClient.getUserById(jwt, id);
    }
}

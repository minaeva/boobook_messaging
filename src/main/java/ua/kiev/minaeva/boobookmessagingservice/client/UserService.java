package ua.kiev.minaeva.boobookmessagingservice.client;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kiev.minaeva.boobookmessagingservice.dto.ReaderDto;
import ua.kiev.minaeva.boobookmessagingservice.exception.BoobookValidationException;

@Service
@Log
public class UserService {

    @Autowired
    private UserClient userClient;

    public ReaderDto getUserByJwt(String jwt) throws BoobookValidationException {
        log.info("MessageService: handling GET USER BY JWT request " + jwt);
        return userClient.getUserByJwt(jwt);
    }
}

package ua.kiev.minaeva.boobookmessagingservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.kiev.minaeva.boobookmessagingservice.dto.ReaderDto;
import ua.kiev.minaeva.boobookmessagingservice.exception.BoobookValidationException;

@Component
public class UserClient {

    @Value("${reader.service.url}")
    private String readerServiceUrl;

    public ReaderDto getUserByJwt(String jwt) throws BoobookValidationException {
        final String url = readerServiceUrl + "/users/jwt";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
        HttpEntity<String> request = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<ReaderDto> response = restTemplate.exchange(url, HttpMethod.GET, request, ReaderDto.class);
            return response.getBody();
        } catch (Exception e) {
            throw new BoobookValidationException("JWT is not valid");
        }
    }
}

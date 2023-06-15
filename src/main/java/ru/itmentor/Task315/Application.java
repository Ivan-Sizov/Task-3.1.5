package ru.itmentor.Task315;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.itmentor.Task315.model.User;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder finalCode = new StringBuilder();

        ResponseEntity<String> response = restTemplate.getForEntity("http://94.198.50.185:7081/api/users", String.class);
        HttpHeaders headers = response.getHeaders();
        String set_cookie = headers.getFirst(HttpHeaders.SET_COOKIE);

        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.add(HttpHeaders.COOKIE, set_cookie);
        User newUser = new User(3L, "James", "Brown", (byte) 25);
        HttpEntity<User> entity = new HttpEntity<>(newUser, newHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange("http://94.198.50.185:7081/api/users",
                HttpMethod.POST, entity, String.class);
        finalCode.append(responseEntity.getBody());

        newUser.setName("Thomas");
        newUser.setLastName("Shelby");
        responseEntity = restTemplate.exchange("http://94.198.50.185:7081/api/users",
                HttpMethod.PUT, entity, String.class);
        finalCode.append(responseEntity.getBody());

        responseEntity = restTemplate.exchange("http://94.198.50.185:7081/api/users/3",
                HttpMethod.DELETE, entity, String.class);
        finalCode.append(responseEntity.getBody());

        System.out.println("Final Code: " + finalCode);
    }
}

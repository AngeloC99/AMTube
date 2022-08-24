package it.uniroma1.ApiGateway.services;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class VerificationService {
    private final static Logger logger = LoggerFactory.getLogger(VerificationService.class);

    @Retryable(backoff = @Backoff(delay = 5000, multiplier = 4.0), maxAttempts = 4)
    public ResponseEntity<String> verificationRequest(String token) throws URISyntaxException {
        URI uri = new URI("http", null, "localhost", 8080, "/auth/verification", null, null);
        logger.info("Sending request for verification to " + uri + "...");

        HttpHeaders headers = new HttpHeaders();

        headers.setAccessControlAllowOrigin("*");
        logger.info("Authorization header: " + token);
        headers.set("Authorization", token);
        logger.info("Verification request's headers: " + headers.toString());

        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestTemplate restTemplate = new RestTemplate(factory);
        try {
            ResponseEntity<String> serverResponse = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
            return serverResponse;
        } catch (HttpStatusCodeException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(401).build();
        }
    }

    public JSONObject parseHttpResponse(ResponseEntity<String> response) throws ParseException {
        Object obj = new JSONParser().parse(response.getBody());
        return (JSONObject) obj;
    }
}
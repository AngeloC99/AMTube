package it.uniroma1.ApiGateway;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProxyService {
    String domain = "AMTube";
    private final static Logger logger = LoggerFactory.getLogger(ProxyService.class);

    @Retryable(exclude = {
            HttpStatusCodeException.class}, include = Exception.class, backoff = @Backoff(delay = 5000, multiplier = 4.0), maxAttempts = 4)
    public ResponseEntity<String> processProxyRequest(String body,
                                                      HttpMethod method, HttpServletRequest request, HttpServletResponse response, String traceId) throws URISyntaxException {
        ThreadContext.put("traceId", traceId);
        String requestUrl = request.getRequestURI();
        //logger.info(requestUrl);

        URI uri = new URI("http",null, "localhost", -1, null, null, null);

        Pattern patternUser = Pattern.compile("/users");
        Pattern patternNotifications = Pattern.compile("/videoNotifications");
        Pattern patternSubscriptions = Pattern.compile("/subscriptions");
        Pattern patternVideos = Pattern.compile("/videos");
        Matcher matcherUser = patternUser.matcher(requestUrl);
        Matcher matcherNotifications = patternNotifications.matcher(requestUrl);
        Matcher matcherSubscriptions = patternNotifications.matcher(requestUrl);
        Matcher matcherVideos = patternVideos.matcher(requestUrl);
        boolean matchUser = matcherUser.find();
        boolean matchNotifications = matcherNotifications.find();
        boolean matchSubscriptions = matcherSubscriptions.find();
        boolean matchVideos = matcherVideos.find();
        int port = -1;
        if(matchUser) {
            logger.info("Users path matched");
            port = 8080;
        }
        else if(matchNotifications || matchSubscriptions) {
            logger.info("Notifications/Subscriptions path matched");
            port = 8081;
        }
        else if(matchVideos) {
            logger.info("Videos path matched");
            port = 8082;
        }
        else {
            logger.info("No match found");
        }
        // replacing context path form urI to match actual gateway URI
        uri = UriComponentsBuilder.fromUri(uri)
                .port(port)
                .path(requestUrl.substring(7))                    // Removing /AMTube
                .query(request.getQueryString())
                .build(true).toUri();
        logger.info(uri.toString());

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.set(headerName, request.getHeader(headerName));
        }

        headers.set("TRACE", traceId);
        headers.remove(HttpHeaders.ACCEPT_ENCODING);


        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestTemplate restTemplate = new RestTemplate(factory);
        try {

            ResponseEntity<String> serverResponse = restTemplate.exchange(uri, method, httpEntity, String.class);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.put(HttpHeaders.CONTENT_TYPE, serverResponse.getHeaders().get(HttpHeaders.CONTENT_TYPE));
            logger.info(serverResponse.toString());
            return serverResponse;


        } catch (HttpStatusCodeException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(e.getRawStatusCode())
                    .headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }

    }
}
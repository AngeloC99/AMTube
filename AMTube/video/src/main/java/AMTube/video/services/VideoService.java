package AMTube.video.services;

import AMTube.video.config.MQConfig;
import AMTube.video.controllers.VideoController;
import AMTube.video.models.Video;
import AMTube.video.models.VideoNotification;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import AMTube.video.repositories.VideoRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {
    private final RabbitTemplate template;
    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final Logger logger = LoggerFactory.getLogger(VideoController.class);

    public VideoService(S3Service s3Service, VideoRepository videoRepository, RabbitTemplate template) {
        this.s3Service = s3Service;
        this.videoRepository = videoRepository;
        this.template = template;
    }

    public Video uploadVideo(MultipartFile multipartFileVideo, MultipartFile multipartFileThumbnail) {
        String videoUrl = s3Service.uploadFile(multipartFileVideo);
        String thumbnailUrl = s3Service.uploadFile(multipartFileThumbnail);
        Video video = new Video();
        video.setVideoUrl(videoUrl);
        video.setThumbnailUrl(thumbnailUrl);
        return videoRepository.save(video);
    }

    public Video editVideo(MultipartFile multipartFile, Video video) {
        String videoUrl = s3Service.uploadFile(multipartFile);
        video.setVideoUrl(videoUrl);
        return videoRepository.save(video);
        
    }

    public Video editThumbnail(MultipartFile file,  Video video) {
        String thumbnailUrl = s3Service.uploadFile(file);
        video.setThumbnailUrl(thumbnailUrl);
        return videoRepository.save(video);
    }

    public void sendVideoNotification(Video video) {
        VideoNotification videoNotification = new VideoNotification();
        videoNotification.setVideoId(video.getId());
        videoNotification.setVideoTitle(video.getTitle());
        videoNotification.setPublisherId(video.getPublisherId());
        logger.info(videoNotification.toString());

        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY_NOTIFICATION, videoNotification);
    }

    // For creating and updating the document representing the video in the proper ElasticSearch index
    public void sendVideoToElastic(Video video) throws URISyntaxException {
        logger.info("Sending to ElasticSearch video: " + video.toString());
        String id = String.valueOf(video.getId());

        // ElasticSearch container on localhost:9200
        URI uri = new URI("http", null, "localhost", 9200, "/videos/_doc/"+id, null, null);
        logger.info("Sending video information to ElasticSearch at " + uri + "...");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccessControlAllowOrigin("*");
        headers.setContentType(MediaType.APPLICATION_JSON);
        logger.info("Request's headers: " + headers);

        JSONObject body  = new JSONObject();
        body.put("videoId", id);
        body.put("title", video.getTitle());
        body.put("description", video.getDescription());

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(body, headers);
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestTemplate restTemplate = new RestTemplate(factory);
        try {
            restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, String.class);
        } catch (HttpStatusCodeException e) {
            logger.error(e.getMessage());
        }
    }

    public void deleteVideoOnElastic(Long videoId) throws URISyntaxException {
        logger.info("Deleting the video" + videoId + "document on ElasticSearch");
        URI uri = new URI("http", null, "localhost", 9200, "/videos/_doc/"+videoId, null, null);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccessControlAllowOrigin("*");
        headers.setContentType(MediaType.APPLICATION_JSON);
        logger.info("Verification request's headers: " + headers);

        HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestTemplate restTemplate = new RestTemplate(factory);
        try {
            restTemplate.exchange(uri, HttpMethod.DELETE, httpEntity, String.class);
        } catch (HttpStatusCodeException e) {
            logger.error(e.getMessage());
        }
    }

    public ResponseEntity<String> searchOnElastic(String query) throws URISyntaxException, ParseException {
        logger.info("Sending query to ElasticSearch...");

        // ElasticSearch container on localhost:9200
        URI uri = new URI("http", null, "localhost", 9200, "/videos/_search", null, null);
        logger.info("Sending query to ElasticSearch at " + uri + "...");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccessControlAllowOrigin("*");
        headers.setContentType(MediaType.APPLICATION_JSON);
        logger.info("Request's headers: " + headers);

        // Building the body of the request to ElasticSearch as follows:
        //  {
        //      "query": {
        //         "multi_match": {
        //            "query" : "bang",
        //            "fields": ["title","description"]
        //            }
        //         }
        //  }
        //

        JSONParser parser=new JSONParser();
        String requestedQuery = "{\"query\": {\"multi_match\": {\"query\" : \"" + query + "\",\"fields\": [\"title\",\"description\"]}}}";
        Object obj = parser.parse(requestedQuery);
        JSONObject body = (JSONObject) obj;

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(body, headers);
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestTemplate restTemplate = new RestTemplate(factory);
        try {
            return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        } catch (HttpStatusCodeException e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}

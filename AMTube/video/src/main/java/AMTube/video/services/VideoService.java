package AMTube.video.services;

import AMTube.video.config.MQConfig;
import AMTube.video.controllers.VideoController;
import AMTube.video.models.Video;
import AMTube.video.models.VideoNotification;
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

@Service
public class VideoService {

    private RabbitTemplate template;
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

    public void sendVideoNotification(Video video) throws URISyntaxException {
        VideoNotification videoNotification = new VideoNotification();
        videoNotification.setVideoId(video.getId());
        videoNotification.setVideoTitle(video.getTitle());
        videoNotification.setPublisherId(video.getPublisherId());
        logger.info(videoNotification.toString());

        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY_NOTIFICATION, videoNotification);
    }
}

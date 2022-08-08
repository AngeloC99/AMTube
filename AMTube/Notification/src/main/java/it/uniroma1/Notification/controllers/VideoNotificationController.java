package it.uniroma1.Notification.controllers;

import it.uniroma1.Notification.MQConfig;
import it.uniroma1.Notification.models.VideoNotification;
import it.uniroma1.Notification.repositories.SubscriptionRepository;
import it.uniroma1.Notification.repositories.VideoNotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videoNotifications")
public class VideoNotificationController {
    @Autowired
    private VideoNotificationRepository videoNotificationRepository;
    private final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);
    @Autowired
    private RabbitTemplate template;

    @GetMapping
    public ResponseEntity<List<VideoNotification>> getAllVideoNotification() {
        List<VideoNotification> videoNotifications = new ArrayList<>();
        videoNotifications.addAll(this.videoNotificationRepository.findAll());
        return ResponseEntity.ok(videoNotifications);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<VideoNotification> getVideoNotificationById(@PathVariable Long notificationId) {
        Optional<VideoNotification> videoNotification = this.videoNotificationRepository.findById(notificationId);

        if (videoNotification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(videoNotification.get());
    }

    @GetMapping("/publisher/{publisherId}")
    public ResponseEntity<List<VideoNotification>> getVideoNotificationsByPublisherId(@PathVariable Long publisherId) {
        List<VideoNotification> videoNotifications = new ArrayList<>();
        videoNotifications.addAll(this.videoNotificationRepository.findByPublisherId(publisherId));
        if (videoNotifications.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(videoNotifications);
    }

    @GetMapping("video/{videoId}")
    public ResponseEntity<VideoNotification> getVideoNotificationByVideoId(@PathVariable Long videoId) {
        Optional<VideoNotification> videoNotification = this.videoNotificationRepository.findByVideoId(videoId);
        if (videoNotification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(videoNotification.get());
    }

    @PostMapping
    public ResponseEntity<VideoNotification> addVideoNotification(@RequestBody VideoNotification videoNotification){
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        videoNotification.setDate(date);
        videoNotification.setTime(time);
        videoNotification = this.videoNotificationRepository.save(videoNotification);
        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY_NOTIFICATION, videoNotification);
        logger.info(videoNotification.toString());
        logger.info("Notification Published");
        return ResponseEntity.status(201).body(videoNotification);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<HttpStatus> deleteSubscription(@PathVariable Long notificationId) {
        try {
            this.videoNotificationRepository.deleteById(notificationId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

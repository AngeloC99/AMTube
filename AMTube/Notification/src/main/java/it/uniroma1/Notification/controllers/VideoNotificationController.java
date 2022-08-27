package it.uniroma1.Notification.controllers;

import it.uniroma1.Notification.config.MQConfig;
import it.uniroma1.Notification.models.Subscription;
import it.uniroma1.Notification.models.VideoNotification;
import it.uniroma1.Notification.repositories.SubscriptionRepository;
import it.uniroma1.Notification.repositories.VideoNotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private final VideoNotificationRepository videoNotificationRepository;
    private final SubscriptionRepository subscriptionRepository;
    private RabbitTemplate template;
    private final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    public VideoNotificationController(VideoNotificationRepository videoNotificationRepository,
                                       SubscriptionRepository subscriptionRepository, RabbitTemplate template) {
        this.videoNotificationRepository = videoNotificationRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.template = template;
    }

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

    @Deprecated
    @GetMapping("/publisher/{publisherId}")
    public ResponseEntity<List<VideoNotification>> getVideoNotificationsByPublisherId(@PathVariable Long publisherId) {
        List<VideoNotification> videoNotifications = new ArrayList<>();
        videoNotifications.addAll(this.videoNotificationRepository.findByPublisherId(publisherId));
        if (videoNotifications.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(videoNotifications);
    }

    @Deprecated
    @GetMapping("video/{videoId}")
    public ResponseEntity<VideoNotification> getVideoNotificationByVideoId(@PathVariable Long videoId) {
        Optional<VideoNotification> videoNotification = this.videoNotificationRepository.findByVideoId(videoId);
        if (videoNotification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(videoNotification.get());
    }

    @GetMapping("receiver/{receiverId}")
    public ResponseEntity<ArrayList<VideoNotification>> getVideoNotificationByReceiverId(@PathVariable Long receiverId) {
        Optional<ArrayList<VideoNotification>> videoNotifications = this.videoNotificationRepository.findByReceiverId(receiverId);
        if (videoNotifications.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ArrayList<VideoNotification> notificationsToReturn = new ArrayList<>();
        for (VideoNotification notification : videoNotifications.get()) {
            if (!notification.isRead()) {
                notificationsToReturn.add(notification);
            }
        }
        this.videoNotificationRepository.saveAllAndFlush(notificationsToReturn);
        if (notificationsToReturn.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notificationsToReturn);
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<VideoNotification> updateVideoNotification(@PathVariable Long notificationId) {
        Optional<VideoNotification> videoNotification = this.videoNotificationRepository.findById(notificationId);
        if (videoNotification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        videoNotification.get().setRead(true);
        this.videoNotificationRepository.saveAndFlush(videoNotification.get());
        return ResponseEntity.ok(videoNotification.get());
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<HttpStatus> deleteNotification(@PathVariable Long notificationId) {
        try {
            this.videoNotificationRepository.deleteById(notificationId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/all")
    public ResponseEntity<HttpStatus> deleteAllNotifications() {
        try {
            this.videoNotificationRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package it.uniroma1.Notification.services;

import it.uniroma1.Notification.models.Subscription;
import it.uniroma1.Notification.models.VideoNotification;
import it.uniroma1.Notification.repositories.SubscriptionRepository;
import it.uniroma1.Notification.repositories.VideoNotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final VideoNotificationRepository videoNotificationRepository;
    private final SubscriptionRepository subscriptionRepository;


    public NotificationService(VideoNotificationRepository videoNotificationRepository,
                               SubscriptionRepository subscriptionRepository) {
        this.videoNotificationRepository = videoNotificationRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public void createNotifications(VideoNotification notification) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        notification.setDate(date);
        notification.setTime(time);

        // Recovery all the subscribers to the user publishing the video
        List<Subscription> subscriptions = this.subscriptionRepository.findBySubscribedToId(notification.getPublisherId());

        for (Subscription subscription : subscriptions) {
            VideoNotification newVidNotification = new VideoNotification();
            newVidNotification.setVideoId(notification.getVideoId());
            newVidNotification.setVideoTitle(notification.getVideoTitle());
            newVidNotification.setPublisherId(notification.getPublisherId());
            newVidNotification.setPublisherUsername(subscription.getSubscribedTo());
            newVidNotification.setReceiverId(subscription.getSubscriberId());
            newVidNotification.setDate(notification.getDate());
            newVidNotification.setTime(notification.getTime());
            var videoNotification = this.videoNotificationRepository.save(newVidNotification);
            logger.info(videoNotification.toString());
            logger.info("Notification Published: " + newVidNotification);
        }
        logger.info("End of notification publishing");
    }
}

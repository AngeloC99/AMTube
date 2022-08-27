package it.uniroma1.Notification.listeners;

import it.uniroma1.Notification.config.MQConfig;
import it.uniroma1.Notification.models.VideoNotification;
import it.uniroma1.Notification.services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class VideoListener {
    private final Logger logger = LoggerFactory.getLogger(VideoListener.class);
    private NotificationService notificationService;

    public VideoListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = MQConfig.QUEUE_NOTIFICATION)
    public void listener(VideoNotification notification) {
        logger.info(notification.toString());
        this.notificationService.createNotifications(notification);
    }
}

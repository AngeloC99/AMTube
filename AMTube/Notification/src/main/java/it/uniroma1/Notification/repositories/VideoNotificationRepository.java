package it.uniroma1.Notification.repositories;

import it.uniroma1.Notification.models.VideoNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface VideoNotificationRepository extends JpaRepository<VideoNotification, Long> {
    Optional<VideoNotification> findByVideoId(Long videoId);
    List<VideoNotification> findByPublisherId(Long publisherId);
    Optional<ArrayList<VideoNotification>> findByReceiverId(Long receiverId);
}

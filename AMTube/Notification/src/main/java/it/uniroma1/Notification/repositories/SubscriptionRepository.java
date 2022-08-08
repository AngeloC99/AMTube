package it.uniroma1.Notification.repositories;

import it.uniroma1.Notification.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    List<Subscription> findBySubscriberId(Long subscriberId);
    List<Subscription> findBySubscribedToId(Long subscribedToId);
}

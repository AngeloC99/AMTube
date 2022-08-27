package it.uniroma1.Notification.controllers;

import it.uniroma1.Notification.models.Subscription;
import it.uniroma1.Notification.repositories.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionController(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.addAll(this.subscriptionRepository.findAll());
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/{subscriptionId}")
    public ResponseEntity<Subscription> getUserById(@PathVariable Long subscriptionId) {
        Optional<Subscription> subscription = this.subscriptionRepository.findById(subscriptionId);
        if (subscription.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subscription.get());
    }

    @GetMapping("/subscriber/{subscriberId}")
    public ResponseEntity<List<Subscription>> getSubscriptionsBySubscriberId(@PathVariable Long subscriberId) {
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.addAll(this.subscriptionRepository.findBySubscriberId(subscriberId));
        if (subscriptions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/subscribedTo/{subscribedToId}")
    public ResponseEntity<List<Subscription>> getSubscriptionsBySubscribedToId(@PathVariable Long subscribedToId) {
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.addAll(this.subscriptionRepository.findBySubscribedToId(subscribedToId));
        if (subscriptions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subscriptions);
    }

    @PostMapping
    public ResponseEntity<Subscription> addSubscription(@RequestBody Subscription subscription){
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        subscription.setDate(date);
        subscription.setTime(time);
        logger.info("Subscription Published");
        return ResponseEntity.status(201).body(this.subscriptionRepository.save(subscription));
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<HttpStatus> deleteSubscription(@PathVariable Long subscriptionId) {
        try {
            this.subscriptionRepository.deleteById(subscriptionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

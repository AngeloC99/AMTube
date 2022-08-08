package it.uniroma1.Notification.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name= "subscriptions")
public class Subscription implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long subscriberId;
    private String subscriber;
    private Long subscribedToId;
    private String subscribedTo;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime time;

    public Subscription() {}

    public Subscription(Long subscriberId, String subscriber, Long subscribedToId, String subscribedTo) {
        this.subscriberId = subscriberId;
        this.subscriber = subscriber;
        this.subscribedToId = subscribedToId;
        this.subscribedTo = subscribedTo;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public Long getId() { return id; }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public String getSubscribedTo() {
        return subscribedTo;
    }

    public void setSubscribedTo(String subscribedTo) {
        this.subscribedTo = subscribedTo;
    }

    public Long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Long subscriberId) {
        this.subscriberId = subscriberId;
    }

    public Long getSubscribedToId() {
        return subscribedToId;
    }

    public void setSubscribedToId(Long getSubscribedToId) {
        this.subscribedToId = getSubscribedToId;
    }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }

    public void setTime(LocalTime time) { this.time = time; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return Objects.equals(id, that.id) && Objects.equals(subscriberId, that.subscriberId) && Objects.equals(subscriber, that.subscriber) && Objects.equals(subscribedToId, that.subscribedToId) && Objects.equals(subscribedTo, that.subscribedTo) && Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subscriberId, subscriber, subscribedToId, subscribedTo, date, time);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", subscriberId=" + subscriberId +
                ", subscriber='" + subscriber + '\'' +
                ", subscribedToId=" + subscribedToId +
                ", subscribedTo='" + subscribedTo + '\'' +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}

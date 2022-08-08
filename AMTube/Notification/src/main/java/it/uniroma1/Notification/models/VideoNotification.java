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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name= "videoNotification")
public class VideoNotification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long videoId;
    private String videoTitle;
    private Long publisherId;
    private String publisherUsername;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime time;

    public VideoNotification() {}

    public VideoNotification(Long videoId, String videoTitle, Long publisherId, String publisherUsername) {
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.publisherId = publisherId;
        this.publisherUsername = publisherUsername;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public Long getId() { return id; }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherUsername() {
        return publisherUsername;
    }

    public void setPublisherUsername(String publisherUsername) {
        this.publisherUsername = publisherUsername;
    }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }

    public void setTime(LocalTime time) { this.time = time; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoNotification that = (VideoNotification) o;
        return Objects.equals(id, that.id) && Objects.equals(videoId, that.videoId) && Objects.equals(videoTitle, that.videoTitle) && Objects.equals(publisherId, that.publisherId) && Objects.equals(publisherUsername, that.publisherUsername) && Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, videoId, videoTitle, publisherId, publisherUsername, date, time);
    }

    @Override
    public String toString() {
        return "VideoNotification{" +
                "id=" + id +
                ", videoId=" + videoId +
                ", videoTitle='" + videoTitle + '\'' +
                ", publisherId=" + publisherId +
                ", publisherUsername='" + publisherUsername + '\'' +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}

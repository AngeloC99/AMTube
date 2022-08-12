package AMTube.video.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import javax.persistence.*;
import java.util.Objects;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long videoId;
    private Long publisherId;
    private String publisherUsername;
    private String text;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;

    public Comment() {}

    public Comment(Long videoId, Long publisherId, String publisherUsername, String text) {
        this.videoId = videoId;
        this.publisherId = publisherId;
        this.publisherUsername = publisherUsername;
        this.text = text;
        this.date = LocalDate.now();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVideoId() {
        return this.videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getPublisherId() { return publisherId; }

    public void setPublisherId(Long publisherId) { this.publisherId = publisherId; }

    public String getPublisherUsername() {
        return this.publisherUsername;
    }

    public void setPublisherUsername(String publisherUsername) {
        this.publisherUsername = publisherUsername;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(videoId, comment.videoId) && Objects.equals(publisherId, comment.publisherId) && Objects.equals(publisherUsername, comment.publisherUsername) && Objects.equals(text, comment.text) && Objects.equals(date, comment.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, videoId, publisherId, publisherUsername, text, date);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + String.valueOf(id) +
                ", videoId='" + String.valueOf(videoId) + '\'' +
                ", publisherId ='" + publisherId + '\'' +
                ", publisherUsername ='" + publisherUsername + '\'' +
                ", text='" + text + '\'' +
                ", date='" + date.toString() + '\'' +
                '}';
    }
}
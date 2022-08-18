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
    private Long publisherId;
    private String publisherUsername;
    private String text;
    private Long videoId;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;

    public Comment() {
    }

    public Comment(Long id, Long publisherId, String publisherUsername, String text, Long videoId, LocalDate date) {
        this.publisherId = publisherId;
        this.publisherUsername = publisherUsername;
        this.text = text;
        this.videoId = videoId;
        this.date = date;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

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

    public Long getVideoId() {
        return this.videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Comment)) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(publisherId, comment.publisherId) && Objects.equals(publisherUsername, comment.publisherUsername) && Objects.equals(text, comment.text) && Objects.equals(videoId, comment.videoId) && Objects.equals(date, comment.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, publisherId, publisherUsername, text, videoId, date);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", publisherId='" + getPublisherId() + "'" +
            ", publisherUsername='" + getPublisherUsername() + "'" +
            ", text='" + getText() + "'" +
            ", videoId='" + getVideoId() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }

}
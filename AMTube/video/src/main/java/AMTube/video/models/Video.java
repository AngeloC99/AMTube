package AMTube.video.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import AMTube.video.models.Comment;
import java.time.LocalDate;
import java.util.*;
import javax.persistence.*;
@Entity
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;
    private Long publisherId;
    @Lob
    private byte[] thumbnail;
    @Lob
    private byte[] data;

    @Transient
    private List<Comment> comments;
    @Transient
    private List<Long> likes; //List of the userIDs of users who liked this video
    public Video() {
    }

    public Video(Long id, String title, String description, List<Long> likes, LocalDate date, Long publisherId, byte[] thumbnail, byte[] data, List<Comment> comments) {
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.date = date;
        this.publisherId = publisherId;
        this.thumbnail = thumbnail;
        this.data = data;
        this.comments = comments;
    }
    public Video(Long id, String title, String description, List<Long> likes, LocalDate date, Long publisherId, byte[] thumbnail, byte[] data) {
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.date = date;
        this.publisherId = publisherId;
        this.thumbnail = thumbnail;
        this.data = data;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getLikes() {
        return this.likes;
    }

    public void setLikes(List<Long> likes) {
        this.likes = likes;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public byte[] getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Video)) {
            return false;
        }
        Video video = (Video) o;
        return Objects.equals(id, video.id) && Objects.equals(title, video.title) && Objects.equals(description, video.description) && likes == video.likes && Objects.equals(date, video.date) && Objects.equals(publisherId, video.publisherId) && Objects.equals(thumbnail, video.thumbnail) && Objects.equals(data, video.data) && Objects.equals(comments, video.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, likes, date, publisherId, thumbnail, data, comments);
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", publisherId='" + getPublisherId() + "'" +
            ", thumbnail='" + getThumbnail() + "'" +
            ", data='" + getData() + "'" +
            ", comments='" + getComments() + "'" +
            ", likes='" + getLikes() + "'" +
            "}";
    }


}
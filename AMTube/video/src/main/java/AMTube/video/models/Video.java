package AMTube.video.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
    private String thumbnailUrl;
    private String videoUrl;

    @Transient
    private List<Comment> comments;
    @Transient
    private List<Long> likes; //List of the userIDs of users who liked this video
    public Video() {
    }

    public Video(Long id, String title, String description, List<Long> likes, LocalDate date, Long publisherId, String thumbnailUrl, String videoUrl, List<Comment> comments) {
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.date = date;
        this.publisherId = publisherId;
        this.thumbnailUrl = thumbnailUrl;
        this.videoUrl = videoUrl;
        this.comments = comments;
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

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnail) {
        this.thumbnailUrl = thumbnail;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(String data) {
        this.videoUrl = data;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return Objects.equals(id, video.id) && Objects.equals(title, video.title) && Objects.equals(description, video.description) && Objects.equals(date, video.date) && Objects.equals(publisherId, video.publisherId) && Objects.equals(thumbnailUrl, video.thumbnailUrl) && Objects.equals(videoUrl, video.videoUrl) && Objects.equals(comments, video.comments) && Objects.equals(likes, video.likes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, date, publisherId, thumbnailUrl, videoUrl, comments, likes);
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", publisherId=" + publisherId +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", comments=" + comments +
                ", likes=" + likes +
                '}';
    }
}
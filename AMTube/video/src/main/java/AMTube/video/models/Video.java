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
    private int likes;
    private int dislikes;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;
    private Long publisherId;
    @Lob
    private byte[] thumbnail;
    @Lob
    private byte[] data;
    public Video() {}

    public Video(String title, String description, int likes, int dislikes, LocalDate date, Long publisherId, byte[] thumbnail, byte[] data) {
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.dislikes = dislikes;
        this.date = LocalDate.now();
        this.publisherId = publisherId;
        this.thumbnail= thumbnail;
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

    public int getLikes() {
        return this.likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return this.dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getPublisherId() { return publisherId; }

    public void setPublisherId(Long publisherId) { this.publisherId = publisherId; }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return likes == video.likes && dislikes == video.dislikes && Objects.equals(id, video.id) && Objects.equals(title, video.title) && Objects.equals(description, video.description) && Objects.equals(date, video.date) && Objects.equals(publisherId, video.publisherId) && Arrays.equals(thumbnail, video.thumbnail) && Arrays.equals(data, video.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, title, description, likes, dislikes, date, publisherId);
        result = 31 * result + Arrays.hashCode(thumbnail);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                ", date=" + date +
                ", publisherId=" + publisherId +
                ", thumbnail=" + Arrays.toString(thumbnail) +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
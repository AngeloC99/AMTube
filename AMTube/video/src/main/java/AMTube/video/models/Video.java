package AMTube.video.models;

import java.util.Date;
import javax.persistence.*;
import java.util.Objects;

@Entity
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private int likes;
    private int dislikes;
    private Date date;
    private String publisher;
    @Lob
    private byte[] thumbnail;

    @Lob
    private byte[] data;

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

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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

    public Video(String title, String description, int likes, int dislikes, Date date, String publisher, byte[] thumbnail, byte[] data) {
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.dislikes = dislikes;
        this.date = date;
        this.publisher = publisher;
        this.thumbnail= thumbnail;
        this.data = data;
    }
    @Override
    public boolean equals(Object o) {
  
      if (this == o)
        return true;
      if (!(o instanceof Video))
        return false;
      Video video = (Video) o;
      return Objects.equals(this.id, video.id) && Objects.equals(this.title, video.title)
          && Objects.equals(this.description, video.description) && Objects.equals(this.date, video.date)
          && Objects.equals(this.likes, video.likes) && Objects.equals(this.dislikes, video.dislikes) 
          && Objects.equals(this.publisher, video.publisher) && Objects.equals(this.thumbnail, video.thumbnail)
          && Objects.equals(this.data, video.data);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, likes, dislikes, date, publisher, thumbnail, data);
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", description='" + description + '\'' +
                ", likes='" + String.valueOf(likes) + '\'' +
                ", dislikes='" + String.valueOf(dislikes) + '\'' +
                ", date='" + date.toString() + '\'' +
                '}';
    }
}
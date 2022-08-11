package AMTube.video.models;

import java.util.Date;
import javax.persistence.*;
import java.util.Objects;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long videoId;
    private String writerUsername;
    private String text;
    private Date date;

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

    public String getWriterUsername() {
        return this.writerUsername;
    }

    public void setWriterUsername(String writerUsername) {
        this.writerUsername = writerUsername;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Comment(Long videoId, String writerUsername, String text, Date date) {
        this.videoId = videoId;
        this.writerUsername = writerUsername;
        this.text = text;
        this.date = date;
    }
    @Override
    public boolean equals(Object o) {
  
      if (this == o)
        return true;
      if (!(o instanceof Comment))
        return false;
      Comment comment = (Comment) o;
      return Objects.equals(this.id, comment.id) && Objects.equals(this.videoId, comment.videoId)
          && Objects.equals(this.text, comment.text) && Objects.equals(this.date, comment.date);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, videoId, writerUsername, text, date);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + String.valueOf(id) +
                ", videoId='" + String.valueOf(videoId) + '\'' +
                ", writerUsername='" + writerUsername + '\'' +
                ", text='" + text + '\'' +
                ", date='" + date.toString() + '\'' +
                '}';
    }
}
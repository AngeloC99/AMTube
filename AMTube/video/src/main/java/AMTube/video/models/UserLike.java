package AMTube.video.models;

import java.util.Objects;
import java.util.*;

import javax.persistence.*;

@Entity
public class UserLike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private Long videoId;

    public UserLike() {
    }

    public UserLike(Long id, Long userId, Long videoId) {
        this.userId = userId;
        this.videoId = videoId;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        if (!(o instanceof UserLike)) {
            return false;
        }
        UserLike like = (UserLike) o;
        return Objects.equals(id, like.id) && Objects.equals(userId, like.userId)
                && Objects.equals(videoId, like.videoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, videoId);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", userId='" + getUserId() + "'" +
                ", videoId='" + getVideoId() + "'" +
                "}";
    }

}

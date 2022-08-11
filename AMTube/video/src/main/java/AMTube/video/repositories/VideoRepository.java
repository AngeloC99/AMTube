package AMTube.video.repositories;

import AMTube.video.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Optional<Video> findById(Long videoId);
    void deleteById(Long videoId);
    boolean existsById(Long videoId);
    List<Video> findByPublisherId(Long publisherId);
}
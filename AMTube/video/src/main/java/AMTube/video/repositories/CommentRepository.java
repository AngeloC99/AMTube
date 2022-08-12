package AMTube.video.repositories;

import AMTube.video.models.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(Long commentId);
    void deleteById(Long commentId);
    boolean existsById(Long commentId);
    List<Comment> findByVideoId(Long videoId);
}

package AMTube.video.repositories;

import AMTube.video.models.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLike, Long>{
    Optional<UserLike> findById(Long id);
    void deleteById(Long id);
    boolean existsById(Long id);
    List<UserLike> findByVideoId(Long videoId);
    List<UserLike> findByUserId(Long videoId);
    Long deleteByVideoId(Long videoId); 

}

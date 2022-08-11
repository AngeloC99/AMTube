package AMTube.video.controllers;

import AMTube.video.models.Video;
import AMTube.video.models.Comment;
import AMTube.video.repositories.CommentRepository;
import AMTube.video.repositories.VideoRepository;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;

import java.util.Optional;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private CommentRepository commentRepository;
    private final Logger logger = LoggerFactory.getLogger(VideoController.class);

    @PostMapping()
    public ResponseEntity<Video> saveVideo(@RequestBody Video newVideo) {
        return ResponseEntity.status(201).body(this.videoRepository.save(newVideo));
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<Video> getVideoById(@PathVariable Long videoId) {
        Optional<Video> video = this.videoRepository.findById(videoId);
        if (video.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(video.get());
    }

    @PutMapping("/{videoId}")
    public ResponseEntity<Video> updateVideoById(@RequestBody Video newVideo, Long videoId) {
        Optional<Video> video = this.videoRepository.findById(videoId);

        if (video.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        logger.info("{}", newVideo);
        video.get().setTitle(newVideo.getTitle());
        video.get().setDescription(newVideo.getDescription());
        video.get().setThumbnail(newVideo.getThumbnail());
        videoRepository.saveAndFlush(video.get());

        return ResponseEntity.status(200).body(video.get());
    }
    @DeleteMapping("/{videoId}")
    public ResponseEntity<Video> deleteVideoById(@PathVariable Long videoId) {
        try {
            this.videoRepository.deleteById(videoId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{videoId}/like")
    public ResponseEntity<Video> addLike(@PathVariable Long videoId) {
        Optional<Video> video = this.videoRepository.findById(videoId);

        if (video.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        video.get().setLikes(video.get().getLikes() + 1);
        return ResponseEntity.ok(video.get());
    }

    @GetMapping("/{videoId}/dislike")
    public ResponseEntity<Video> addDislike(@PathVariable Long videoId) {
        Optional<Video> video = this.videoRepository.findById(videoId);

        if (video.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        video.get().setDislikes(video.get().getDislikes() + 1);
        return ResponseEntity.ok(video.get());
    }

    @PostMapping("/{videoId}/comment")
    public ResponseEntity<Comment> saveComment(@PathVariable Long videoId, @RequestBody Comment newComment) {
        return ResponseEntity.status(201).body(this.commentRepository.save(newComment));
    }

    @DeleteMapping("/{videoId}/comment/{commentId}")
    public ResponseEntity<Comment> deleteCommentById(@PathVariable Long commentId) {
        try {
            this.commentRepository.deleteById(commentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
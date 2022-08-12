package AMTube.video.controllers;

import AMTube.video.models.Video;
import AMTube.video.models.Comment;
import AMTube.video.repositories.CommentRepository;
import AMTube.video.repositories.VideoRepository;

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
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@RestController
@RequestMapping("/videos")
public class VideoController {
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private CommentRepository commentRepository;
    private final Logger logger = LoggerFactory.getLogger(VideoController.class);

    @GetMapping
    public ResponseEntity<List<Video>> getAllVideo() {
        List<Video> videos = new ArrayList<Video>();
        videos.addAll(this.videoRepository.findAll());
        return ResponseEntity.ok(videos);
    }

    @PostMapping()
    public ResponseEntity<Video> saveVideo(@RequestBody Video newVideo) {
        LocalDate date = LocalDate.now();
        newVideo.setDate(date);
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
    public ResponseEntity<Video> updateVideo(@PathVariable Long videoId, @RequestBody Video newVideo) {
        Optional<Video> video = this.videoRepository.findById(videoId);

        if (video.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        logger.info("{}", newVideo);
        video.get().setTitle(newVideo.getTitle());
        video.get().setDescription(newVideo.getDescription());
        video.get().setLikes(newVideo.getLikes());
        video.get().setDislikes(newVideo.getDislikes());
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

    @PostMapping("/{videoId}/comments")
    public ResponseEntity<Comment> saveComment(@PathVariable Long videoId, @RequestBody Comment newComment) {
        return ResponseEntity.status(201).body(this.commentRepository.save(newComment));
    }

    // TO MODIFY FOR UPDATING VIDEO COMMENTS
    @DeleteMapping("/{videoId}/comments/{commentId}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long videoId, @PathVariable Long commentId) {
        try {
            this.commentRepository.deleteById(commentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
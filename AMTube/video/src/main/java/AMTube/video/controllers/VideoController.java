package AMTube.video.controllers;

import AMTube.video.models.Video;
import AMTube.video.models.Comment;
import AMTube.video.models.UserLike;
import AMTube.video.repositories.CommentRepository;
import AMTube.video.repositories.UserLikeRepository;
import AMTube.video.repositories.VideoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
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
    @Autowired
    private UserLikeRepository userLikeRepository;
    private final Logger logger = LoggerFactory.getLogger(VideoController.class);

    @GetMapping
    public ResponseEntity<List<Video>> getAllVideo() {
        List<Video> videos = new ArrayList<Video>();
        videos.addAll(this.videoRepository.findAll());
        return ResponseEntity.ok(videos);
    }

    @PostMapping
    public ResponseEntity<Video> saveVideo(@RequestParam("file") MultipartFile file) throws IOException {
        LocalDate date = LocalDate.now();
        Video newVideo = new Video();
        newVideo.setDate(date);
        newVideo.setData(file.getBytes());
        logger.info("new Video");
        return ResponseEntity.status(201).body(this.videoRepository.save(newVideo));
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<Video> getVideoById(@PathVariable Long videoId) {
        Optional<Video> video = this.videoRepository.findById(videoId);
        if (video.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Comment> comments = this.commentRepository.findByVideoId(videoId);
        List<UserLike> likes = this.userLikeRepository.findByVideoId(videoId);
        List<Long> ids = new ArrayList<Long>();
        for (UserLike like: likes){
            ids.add(like.getUserId());
        }
        Video vid = video.get();
        vid.setComments(comments);
        vid.setLikes(ids);
        return ResponseEntity.ok(vid);
    }

    /*@PutMapping
    public ResponseEntity<Video> updateVideo(@RequestBody Video newVideo, @RequestParam(name= "file", required = false) MultipartFile thumbnail) throws IOException {
        Optional<Video> video = this.videoRepository.findById(newVideo.getId());

        if (video.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        logger.info("{}", newVideo);
        video.get().setTitle(newVideo.getTitle());
        video.get().setDescription(newVideo.getDescription());
        video.get().setPublisherId(newVideo.getPublisherId());
        video.get().setThumbnail(thumbnail.getBytes());
        videoRepository.saveAndFlush(video.get());

        return ResponseEntity.status(200).body(video.get());
    }*/


    @PutMapping("/{videoId}")
    public ResponseEntity<Video> updateVideo(@PathVariable Long videoId, @RequestBody Video newVideo) {
        Optional<Video> video = this.videoRepository.findById(videoId);

        if (video.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        logger.info("{}", newVideo);
        video.get().setTitle(newVideo.getTitle());
        video.get().setDescription(newVideo.getDescription());
        video.get().setPublisherId(newVideo.getPublisherId());
        videoRepository.saveAndFlush(video.get());

        return ResponseEntity.status(200).body(video.get());
    }

    @PutMapping("/{videoId}/thumbnail")
    public ResponseEntity<Video> saveThumbnail(@PathVariable Long videoId, @RequestParam("thumbnail") MultipartFile thumbnail) throws IOException {
        Optional<Video> video = this.videoRepository.findById(videoId);

        if (video.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        video.get().setThumbnail(thumbnail.getBytes());
        videoRepository.saveAndFlush(video.get());

        return ResponseEntity.status(201).body(video.get());
    }

    @DeleteMapping("/{videoId}")
    public ResponseEntity<Video> deleteVideoById(@PathVariable Long videoId) {
        try {
            this.videoRepository.deleteById(videoId);
            this.commentRepository.deleteByVideoId(videoId);
            this.userLikeRepository.deleteByVideoId(videoId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{videoId}/comments")
    public ResponseEntity<Comment> saveComment(@PathVariable Long videoId, @RequestBody Comment newComment) {
        Optional<Video> video = this.videoRepository.findById(videoId);

        if (video.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        newComment.setVideoId(videoId);
        return ResponseEntity.status(201).body(this.commentRepository.save(newComment));
    }

    @PostMapping("/{videoId}/like/{userId}")
    public ResponseEntity<UserLike> likeVideo(@PathVariable Long videoId, @PathVariable Long userId) {
        Optional<Video> video = this.videoRepository.findById(videoId);
        if (video.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<UserLike> likes = this.userLikeRepository.findByVideoId(videoId);
        for (UserLike like : likes) {
            if (like.getUserId() == userId) {
                return ResponseEntity.status(200).body(like); // Cambiare 200 con il codice per un entità già presente
            }
        }
        UserLike newLike = new UserLike();
        newLike.setUserId(userId);
        newLike.setVideoId(videoId);
        this.userLikeRepository.save(newLike);
        return ResponseEntity.status(201).body(newLike);
    }

    @DeleteMapping("/{videoId}/like/{userId}")
    public ResponseEntity<UserLike> unlikeVideo(@PathVariable Long videoId, @PathVariable Long userId) {
        Optional<Video> video = this.videoRepository.findById(videoId);
        if (video.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<UserLike> likes = this.userLikeRepository.findByVideoId(videoId);
        for (UserLike like : likes) {
            if (like.getUserId() == userId) {
                this.userLikeRepository.deleteById(like.getId());
                return ResponseEntity.status(200).body(like); 
            }
        }
        return ResponseEntity.notFound().build();
    }

    // TO MODIFY FOR UPDATING VIDEO COMMENTS
    @DeleteMapping("/{videoId}/comments/{commentId}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long videoId, @PathVariable Long commentId) {
        try {
            if(!this.commentRepository.existsById(commentId)){
                return ResponseEntity.notFound().build();
            }
            this.commentRepository.deleteById(commentId);
            return ResponseEntity.status(200).build(); 
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
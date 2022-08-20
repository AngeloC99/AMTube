package AMTube.video.services;

import AMTube.video.models.Video;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import AMTube.video.repositories.VideoRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;

    public VideoService(S3Service s3Service, VideoRepository videoRepository) {
        this.s3Service = s3Service;
        this.videoRepository = videoRepository;
    }


    public Video uploadVideo(MultipartFile multipartFile) {
        String videoUrl = s3Service.uploadFile(multipartFile);
        Video video = new Video();
        video.setVideoUrl(videoUrl);
        return videoRepository.save(video);
    }

    public String uploadThumbnail(MultipartFile file, String videoId) {
        Optional<Video> savedVideo = videoRepository.findById(Long.parseLong(videoId));
        String thumbnailUrl = s3Service.uploadFile(file);
        savedVideo.get().setThumbnailUrl(thumbnailUrl);
        videoRepository.save(savedVideo.get());
        return thumbnailUrl;
    }
}

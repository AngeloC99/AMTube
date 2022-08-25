package AMTube.video.services;

import AMTube.video.models.Video;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import AMTube.video.repositories.VideoRepository;

@Service
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;

    public VideoService(S3Service s3Service, VideoRepository videoRepository) {
        this.s3Service = s3Service;
        this.videoRepository = videoRepository;
    }

    public Video uploadVideo(MultipartFile multipartFileVideo, MultipartFile multipartFileThumbnail) {
        String videoUrl = s3Service.uploadFile(multipartFileVideo);
        String thumbnailUrl = s3Service.uploadFile(multipartFileThumbnail);
        Video video = new Video();
        video.setVideoUrl(videoUrl);
        video.setThumbnailUrl(thumbnailUrl);
        return videoRepository.save(video);
    }

    public Video editVideo(MultipartFile multipartFile, Video video) {
        String videoUrl = s3Service.uploadFile(multipartFile);
        video.setVideoUrl(videoUrl);
        return videoRepository.save(video);
        
    }

    public Video editThumbnail(MultipartFile file,  Video video) {
        String thumbnailUrl = s3Service.uploadFile(file);
        video.setThumbnailUrl(thumbnailUrl);
        return videoRepository.save(video);
    }
}

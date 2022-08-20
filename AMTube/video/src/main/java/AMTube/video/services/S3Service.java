package AMTube.video.services;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {
    public static final String BUCKET_NAME = "amtube-bucket";
    private final AmazonS3Client awsS3Client;

    public S3Service(AmazonS3Client awsS3Client) {
        this.awsS3Client = awsS3Client;
    }

    public String uploadFile(MultipartFile file) {
        // Get the extension of the file
        String filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        // Create ID for the video
        String key = UUID.randomUUID().toString() + "." + filenameExtension;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            awsS3Client.putObject(BUCKET_NAME, key, file.getInputStream(), metadata);
        } catch (IOException ioException) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "A problem occurred when uploading the file");
        }

        // To read the file publicly from frontend without authentication on AWS
        awsS3Client.setObjectAcl(BUCKET_NAME, key, CannedAccessControlList.PublicRead);

        return awsS3Client.getResourceUrl(BUCKET_NAME, key);
    }
}

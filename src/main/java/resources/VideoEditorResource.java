package resources;

import excpetions.VideoEditException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import requests.VideoTrimRequest;
import responses.VideoEditResponse;
import responses.VideoUploadResponse;
import service.GoogleDriveServiceImpl;
import service.VideoEditService;
import service.VideoEditServiceImpl;

import java.io.IOException;

@Slf4j
public class VideoEditorResource {
        private VideoEditService videoEditService  = new VideoEditServiceImpl(new GoogleDriveServiceImpl());

        @PostMapping("/upload")
        public VideoEditResponse uploadVideo(@RequestParam("file") MultipartFile file) throws VideoEditException {
                return videoEditService.uploadVideo(file);
        }

        @PostMapping("/trim")
        public VideoEditResponse trimVideo(VideoTrimRequest videoTrimRequest) throws IOException, VideoEditException {
                return videoEditService.trimVideo(videoTrimRequest);
        }

        @PostMapping("/merge")

        @GetMapping("/getUploadedVideos")
        public VideoUploadResponse getUploadedVideos(@RequestParam("limit") int limit) throws VideoEditException {
                return videoEditService.getUploadedVideos(limit);

        }

}

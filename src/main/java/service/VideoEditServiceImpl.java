package service;

import com.google.api.services.drive.model.FileList;
import constants.Constants;
import excpetions.VideoEditException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import requests.VideoMergeRequest;
import requests.VideoTrimRequest;
import responses.VideoEditResponse;
import responses.VideoUploadResponse;
import validator.VideoValidator;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
public class VideoEditServiceImpl implements VideoEditService {

    private final GoogleDriveService googleDriveService;

    public VideoEditServiceImpl(GoogleDriveService googleDriveService) {
        this.googleDriveService = googleDriveService;
    }


    @Override
    public VideoEditResponse uploadVideo(MultipartFile file) throws VideoEditException {
        try {
            List<String> validations = new ArrayList<>();
            File uploadedFile = File.createTempFile("upload_", file.getOriginalFilename());
            file.transferTo(uploadedFile);
            Boolean isValid  = VideoValidator.validateVideoDuration(uploadedFile.getAbsolutePath(), validations, file.getSize());
            uploadedFile.delete();

            //file.transferTo(new File(filePath));
            String filePath  = googleDriveService.uploadFile(Constants.FILE_MIMETYPE, file.getName());
            if (isValid) {
                return new VideoEditResponse(true, Arrays.asList("File loaded successfully"), filePath);
            }
            else {
                return new VideoEditResponse(false, validations, null);
            }
        } catch (Exception ex) {
            log.error(String.valueOf(ex.getStackTrace()));
            throw new VideoEditException("Error while uploading file please try agan in sometime: ", ex);
        }

    }

    @Override
    public VideoEditResponse mergeVideo(MultipartFile file, VideoMergeRequest videoMergeRequest) {

        return null;
    }

    @Override
    public VideoEditResponse trimVideo(VideoTrimRequest videoTrimRequest) throws VideoEditException {
        List<String> uploadedFiles = googleDriveService.getUploadedVideos();
        if(!uploadedFiles.contains(videoTrimRequest.getFileName())) {
            return new VideoEditResponse(false, Arrays.asList("Given file not uploaded yet"), null);
        }
        File uploadedFile = googleDriveService.getFile(videoTrimRequest.getFileName());
        try {
        } catch (Exception ex) {
            log.error(String.valueOf(ex.getStackTrace()));
            throw new VideoEditException("Error while uploading video please try again");
        }
        String filePath = Constants.UPLOAD_LOCATION + "_"+ uploadedFile.getName();

        String[] command = {
                "ffmpeg",
                "-i", uploadedFile.getName(),    // Input file
                "-ss", videoTrimRequest.getStartTime(),       // Start time (e.g., "00:00:05")
                "-t", videoTrimRequest.getEndTime(),         // Duration (e.g., "00:00:10")
                "-c", "copy",           // Copy codec to avoid re-encoding
                filePath
        };

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                log.info("Video trimmed successfully!");
                return new VideoEditResponse(true, Arrays.asList("Video trimmed successfully"), filePath);
            } else {
                log.info("Error while trimming the video.");
                return new VideoEditResponse(false, Arrays.asList("Error while trimming the video."), null);
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            log.error(String.valueOf(e.getStackTrace()));
            throw new VideoEditException("Trimming process was interrupted", e);
        }
    }

    @Override
    public VideoUploadResponse getUploadedVideos(int limit) throws VideoEditException {

        List<String> uploadedVideos = googleDriveService.getUploadedVideos();
        if(Objects.nonNull(uploadedVideos)) {
            return new VideoUploadResponse(uploadedVideos.size() > limit ? uploadedVideos.subList(0, limit) : uploadedVideos);
        }
        return null;
    }


}

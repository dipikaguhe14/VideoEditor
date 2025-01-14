package service;

import excpetions.VideoEditException;
import org.springframework.web.multipart.MultipartFile;
import requests.VideoMergeRequest;
import requests.VideoTrimRequest;
import responses.VideoEditResponse;
import responses.VideoUploadResponse;

import java.io.IOException;

public interface VideoEditService {

    VideoEditResponse uploadVideo(MultipartFile file) throws VideoEditException;

    VideoEditResponse mergeVideo(MultipartFile file, VideoMergeRequest videoMergeRequest);

    VideoEditResponse trimVideo(VideoTrimRequest videoTrimRequest) throws IOException, VideoEditException;

    VideoUploadResponse getUploadedVideos(int limit) throws VideoEditException;


}

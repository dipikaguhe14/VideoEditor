package responses;

import java.util.List;

public class VideoUploadResponse {
    List<String> videos;

    public static VideoUploadResponse ok(List<String> videos) {
        return new VideoUploadResponse(videos);
    }

    public static VideoUploadResponse error() {
        return new VideoUploadResponse(null);
    }

    public VideoUploadResponse(List<String> videos) {
        this.videos = videos;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    public VideoUploadResponse() {
    }

    @Override
    public String toString() {
        return "VideoUploadResponse{" +
                "videos=" + videos +
                '}';
    }
}


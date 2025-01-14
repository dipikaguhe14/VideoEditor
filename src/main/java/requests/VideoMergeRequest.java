package requests;

public class VideoMergeRequest {
    VideoTrimRequest video1;
    VideoTrimRequest video2;

    public VideoMergeRequest(VideoTrimRequest video1, VideoTrimRequest video2) {
        this.video1 = video1;
        this.video2 = video2;
    }

    public VideoMergeRequest() {
    }

    public VideoTrimRequest getVideo1() {
        return video1;
    }

    public void setVideo1(VideoTrimRequest video1) {
        this.video1 = video1;
    }

    public VideoTrimRequest getVideo2() {
        return video2;
    }

    public void setVideo2(VideoTrimRequest video2) {
        this.video2 = video2;
    }

    @Override
    public String toString() {
        return "VideoMergeRequest{" +
                "video1=" + video1 +
                ", video2=" + video2 +
                '}';
    }
}

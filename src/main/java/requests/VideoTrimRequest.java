package requests;

public class VideoTrimRequest {
    private String startTime; // Start time (e.g., "00:00:05")
    private String endTime;  // End time (e.g., "00:00:10")
    private String fileName;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public VideoTrimRequest() {
    }

    public VideoTrimRequest(String startTime, String endTime, String fileName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "VideoTrimRequest{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}

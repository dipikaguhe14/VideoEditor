package responses;


import java.util.List;


public class VideoEditResponse {

    private Boolean isValid;
    private List<String> validationList;
    private String path;


    public VideoEditResponse() {
    }


    public static VideoEditResponse ok(String message, String path) {
        return new VideoEditResponse(true, List.of(message), path);
    }

    public static VideoEditResponse error(List<String> errors, String path) {
        return new VideoEditResponse(false, errors, path);
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public List<String> getValidationList() {
        return validationList;
    }

    public void setValidationList(List<String> validationList) {
        this.validationList = validationList;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public VideoEditResponse(Boolean isValid, List<String> validationList, String path) {
        this.isValid = isValid;
        this.validationList = validationList;
        this.path = path;
    }

    @Override
    public String toString() {
        return "VideoEditResponse{" +
                "isValid=" + isValid +
                ", validationList=" + validationList +
                ", path='" + path + '\'' +
                '}';
    }
}
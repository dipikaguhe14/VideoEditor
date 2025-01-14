package excpetions;

public class VideoEditException extends Exception {

    public VideoEditException(String message) {
        super(message);
    }

    public VideoEditException(String message, Throwable cause) {
        super(message, cause);
    }
}
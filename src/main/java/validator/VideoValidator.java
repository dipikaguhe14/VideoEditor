package validator;

import constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.global.avformat;
import org.bytedeco.javacpp.PointerPointer;

import java.util.List;

@Slf4j
public class VideoValidator {

    public static boolean validateVideoDuration(String filePath, List<String> validations, long fileSize) {
        AVFormatContext formatContext = avformat.avformat_alloc_context();
        if ( fileSize>  Constants.maximumSizeInMb * 1024 * 1024) {
            validations.add("Video exceeds allowed size of: "+ Constants.maximumSizeInMb);
            log.info( "Video exceeds allowed size of: "+ Constants.maximumSizeInMb);
        }
        if (fileSize < Constants.minimumSizeInMb * 1024 * 1024) {
            validations.add("Video is less than allowed size of: "+ Constants.minimumSizeInMb);
            log.info("Video is less than allowed size of: "+ Constants.minimumSizeInMb);
        }

        if (avformat.avformat_open_input(formatContext, filePath, null, null) < 0) {
            validations.add("could not open file the file might be corrupted");
            return false;
        }

        if (avformat.avformat_find_stream_info(formatContext, (PointerPointer) null) < 0) {
            validations.add("Could not retrieve stream info from file: " + filePath);
            return false;
        }

        long durationInSeconds = formatContext.duration() / 1_000_000;
        if(durationInSeconds > Constants.maximumDurationInSec) {
            validations.add("The video duration is greater than maximum limit allowed");
            return false;
        }

        if(durationInSeconds < Constants.minimumDurationInSec) {
            validations.add("The video duration is less than allowed limit");
            return false;
        }

        avformat.avformat_close_input(formatContext);

        return true;
    }
}
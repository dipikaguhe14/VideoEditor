package service;

import com.google.api.client.auth.oauth2.Credential;
import java.io.File;
import excpetions.VideoEditException;

import java.util.List;

public interface GoogleDriveService {

    String getFileLink(String fileName) throws VideoEditException;
    String uploadFile( String mimeType, String fileName) throws VideoEditException;
    List<String> getUploadedVideos() throws VideoEditException;
    File getFile(String fileName) throws VideoEditException;

}

package service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import constants.Constants;
import excpetions.VideoEditException;
import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
public class GoogleDriveServiceImpl implements GoogleDriveService {

    private static final String APPLICATION_NAME = "VideoEditorDrive";
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/gdrive-secret.json";

    @Override
    public String getFileLink(String fileName) throws VideoEditException {
        try {
            return getShareableLink(getDriveService(), fileName);
        } catch (Exception ex) {
            log.error("Error getting link from google drive: ", ex);
            throw new VideoEditException("Error getting link from google drive: ", ex);
        }

    }

    /****
     *
     * @param mimeType "video/mp4"
     * @param fileName "filename.mp4";
     */
    @Override
    public String uploadFile( String mimeType, String fileName) throws VideoEditException {
        try {
            Drive service = getDriveService();

            File fileMetadata = new File();
            fileMetadata.setName(fileName);

            java.io.File file = new java.io.File(Constants.UPLOAD_LOCATION);
            FileContent mediaContent = new FileContent(mimeType, file);
            File uploadedFile = service.files().create(fileMetadata, mediaContent)
                    .setFields("id, webContentLink, webViewLink")
                    .execute();

            log.info("File uploaded successfully!");

            return getShareableLink(service, uploadedFile.getName());
        } catch (Exception e) {
            e.printStackTrace();
            throw new VideoEditException("Error uploading file on Google drive ", e);
        }
    }

    @Override
    public List<String> getUploadedVideos() throws VideoEditException {
        try {
            List<String> uploadedFiles = new ArrayList<>();
            String query = "'" + Constants.UPLOAD_LOCATION + "' in parents and trashed=false";
            FileList result = getDriveService().files().list()
                    .setQ(query)
                    .setSpaces("drive")
                    .setFields("nextPageToken, files(id, name, mimeType)")
                    .execute();

            for (File file : result.getFiles()) {
                uploadedFiles.add(file.getName());
            }

            return uploadedFiles;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new VideoEditException("Error while fetching file from google drive ", ex);
        }

    }

    private String getShareableLink(Drive service, String fileName) throws IOException, VideoEditException {
        File file = getGDriveFile(fileName);
        if(Objects.nonNull(file)) {
            Permission permission = new Permission()
                    .setType("anyone")
                    .setRole("reader"); // Allows anyone with the link to read the file
            service.permissions().create(file.getId(), permission).execute();

            return "https://drive.google.com/file/d/" + file.getId() + "/view?usp=sharing";
        }

        return null;
    }


    private File getGDriveFile(String fileName) throws VideoEditException {
        String query = "name='" + fileName + "' and trashed=false";
        try{
            File file = getDriveService().files().list()
                    .setQ(query)
                    .setFields("files(id, name)")
                    .execute()
                    .getFiles()
                    .stream()
                    .findFirst()
                    .orElse(null);

            return file;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new VideoEditException("There was an error fetching file from google drive ", ex);
        }
    }
    @Override
    public java.io.File getFile(String fileName) {
        java.io.File localFile = new java.io.File(Constants.UPLOAD_LOCATION);
        try (OutputStream outputStream = new FileOutputStream(localFile)) {
            getDriveService().files().get(fileName)
                    .executeMediaAndDownloadTo(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }

        return localFile;

    }

    private Drive getDriveService() throws Exception {
        InputStream in = GoogleDriveServiceImpl.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new Exception("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, SCOPES)
                .setAccessType("offline")
                .build();

        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        return new Drive.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }


}

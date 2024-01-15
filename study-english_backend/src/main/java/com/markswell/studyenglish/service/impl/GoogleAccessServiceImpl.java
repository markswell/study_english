package com.markswell.studyenglish.service.impl;

import com.google.api.services.drive.Drive;
import com.google.api.client.json.JsonFactory;
import org.springframework.stereotype.Service;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.DriveScopes;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.model.FileList;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.web.bind.annotation.PathVariable;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.markswell.studyenglish.service.GoogleAccessService;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;

import java.io.*;
import java.util.List;
import java.util.Comparator;
import java.security.GeneralSecurityException;

import static java.util.Collections.*;

@Service
public class GoogleAccessServiceImpl implements GoogleAccessService {

    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "StudyEnglish";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    @Override
    public String getFilesNames() throws IOException, GeneralSecurityException {

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        FileList result = service.files().list().setAlt("")
                .setPageSize(1000)
                .setFields("nextPageToken, files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            return "No files found.";
        } else {
            System.out.println("Files:");
            var stringBuilder = new StringBuilder();
            int count = 0;
            sort(files, Comparator.comparing(File::getName));
            for (File file : files) {
                stringBuilder.append("%d %s %s\n".formatted(++count, file.getName(), file.getId()));
            }
            return stringBuilder.toString();
        }
    }

    @Override
    public byte[] getFile(@PathVariable("realFileId") String realFileId) throws IOException, GeneralSecurityException {

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Credential credentials = getCredentials(HTTP_TRANSPORT);

        Drive service = new Drive.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                credentials)
                .setApplicationName("StudyEnglish")
                .build();

        try(OutputStream outputStream = new ByteArrayOutputStream()) {
            service.files()
                    .get(realFileId)
                    .executeMediaAndDownloadTo(outputStream);

            return ((ByteArrayOutputStream)outputStream).toByteArray();
        } catch (GoogleJsonResponseException e) {
            System.err.println("Unable to export file: " + e.getDetails());
            throw e;
        }
    }

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = GoogleAccessServiceImpl.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setClientId("331452091122-8fvtbreo0k4laslmjadknv3s4kja5dc5.apps.googleusercontent.com")
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        return credential;
    }

}
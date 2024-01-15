package com.markswell.studyenglish.service.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.google.api.client.util.store.FileDataStoreFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import java.security.GeneralSecurityException;

/* class to demonstrate use of Drive files list API */
@RestController
@RequestMapping("/teste")
public class DriveQuickstart {

    private static final String API_KEY = "AIzaSyCGnSOkJYttMATVY14rqqvqVgrA0XcvYUI";
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
    private static final List<String> SCOPES =
            Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = DriveQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
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

        //returns an authorized Credential object.
        return credential;
    }

    @GetMapping
    public void teste() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Print the names and IDs for up to 10 files.
        FileList result = service.files().list().setAlt("")
                .setPageSize(1000)
                .setFields("nextPageToken, files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            var stringBuilder = new StringBuilder();
            int count = 0;
            Collections.sort(files, Comparator.comparing(File::getName));
            for (File file : files) {
                System.out.printf("%s (%s)\n", file.getName(), file.getId());
                if(file.getName().startsWith("book"))
                    stringBuilder.append("%d %s %s\n".formatted(++count, file.getName(), file.getId()));
//                System.out.printf("%s (%s) (%s) tem pertmissão (%s)\n", file.getName(), file.getId(), file.getDriveId(), file.getCopyRequiresWriterPermission());
            }
            new java.io.File("/home/markswell/fileIds.txt").createNewFile();
            try(FileWriter myWriter = new FileWriter("/home/markswell/fileIds.txt")) {
                myWriter.write(stringBuilder.toString());
            } catch (Exception e) {}
        }
    }

    @GetMapping("/download/{realFileId}")
    public static byte[] exportPdf(@PathVariable("realFileId") String realFileId) throws IOException, GeneralSecurityException {

        // Load pre-authorized user credentials from the environment.
        // TODO(developer) - See https://developers.google.com/identity for
        // guides on implementing OAuth2 for your application.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Credential credentials = getCredentials(HTTP_TRANSPORT);

        // Build a new authorized API client service.
        Drive service = new Drive.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                credentials)
                .setApplicationName("StudyEnglish")
                .build();


        try(OutputStream outputStream = new ByteArrayOutputStream()) {

            service.files().get(realFileId)
                    .executeMediaAndDownloadTo(outputStream);

            return ((ByteArrayOutputStream)outputStream).toByteArray();
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            System.err.println("Unable to export file: " + e.getDetails());
            throw e;
        }
    }

}
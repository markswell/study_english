package com.markswell.studyenglish.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.markswell.studyenglish.service.MediaService;
import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;


import static java.nio.file.Files.list;
import static com.markswell.studyenglish.util.BookPathUtil.getBooks;
import static org.springframework.http.MediaType.parseMediaType;

@Service
public class MediaServiceImpl implements MediaService {

    @Value("${media.context-path}")
    private String pathFiles;

    @Override
    public byte[] getPdf(String book) {
        try {
            Path first = getBookPath(book);
            Path path = list(first).filter(f -> f.toString().endsWith(".pdf")).toList().get(0);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public byte[] getPdfByLesson(Long book, Long lessonId) {
        try {
            Path bookPath = getBook(book, lessonId);
            Path path = list(bookPath).filter(p -> p.toString().endsWith(".pdf")).findFirst().get();
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<byte[]> getAudioByRange(Long bookId, Long lessonId, Long audioId, String rangeHeader) {
        return getResponseEntityByRange(rangeHeader, getAudio(bookId, lessonId, audioId), "audio/mpeg");
    }

    @Override
    public ResponseEntity<byte[]> getVideoByRage(Long classId, String rangeHeader) {
        try {
            return getResponseEntityByRange(rangeHeader, getVideo(classId), "video/mp4");
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public byte[] getThumbnail(Long classId) {
        try {
            Path path = list(Paths.get(pathFiles.concat("/classes")))
                    .filter(f -> f.toString().endsWith(".png"))
                    .filter(f -> isIdEquals(classId, f))
                    .findFirst()
                    .get();
            return Files.readAllBytes(path);
        } catch (IOException e) {
            return null;
        }
    }

    private static ResponseEntity<byte[]> getResponseEntityByRange(String rangeHeader, byte[] bytes, String mediaType) {
        try {
            if(Objects.isNull(rangeHeader)) {
                return ResponseEntity.ok()
                        .contentType(parseMediaType(mediaType))
                        .contentLength(bytes.length)
                        .header("Accept-Ranges", "bytes")
                        .body(bytes);
            }
            String[] ranges = rangeHeader.substring("bytes=".length()).split("-");
            int from = Integer.parseInt(ranges[0]);
            int to = ranges.length > 1 ? Integer.parseInt(ranges[1]) : bytes.length - 1;

            byte[] partialData = Arrays.copyOfRange(bytes, from, to + 1);

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .contentType(parseMediaType(mediaType))
                    .header("Accept-Ranges", "bytes")
                    .header("Content-Range", "bytes " + from + "-" + to + "/" + bytes.length)
                    .contentLength(partialData.length)
                    .body(partialData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
                    .header("Content-Range", "bytes */" + bytes.length)
                    .build();
        }
    }

    private Path getBook(Long bookId, Long lessonId) throws IOException {
        return list(Paths.get(pathFiles))
                .filter(f -> f.toString().contains("_book_"))
                .filter(f -> isIdEquals(bookId, f))
                .flatMap(f -> {
                    try {
                        return list(f)
                                .filter(g -> !g.toString().endsWith(".pdf"))
                                .filter(g -> isIdEquals(lessonId, g));
                    } catch (IOException e) {
                        return null;
                    }
                }).findFirst().get();
    }

    private Path getBookPath(String book) throws IOException {
        return getBooks(pathFiles).filter(f -> f.toString().contains(book)).findFirst().get();
    }

    private static boolean isIdEquals(Long bookId, Path f) {
        return bookId.equals(Long.parseLong(f.getFileName().toString().split("_")[0]));
    }

    private byte[] getAudio(Long bookId, Long lessonId, Long audioId) {
        try {

            Path path = getBook(bookId, lessonId);
            String audioName = list(path)
                    .map(f -> f.getFileName().toString())
                    .filter(f -> !f.endsWith(".pdf") && audioId.equals(Long.parseLong(f.substring(f.length() - 6).replace(".mp3", ""))))
                    .findFirst()
                    .get();
            return Files.readAllBytes(Path.of(path.toString().concat("/").concat(audioName)));
        } catch (IOException e) {
            return null;
        }
    }

    private byte[] getVideo(Long classId) throws IOException {
        Path path = list(Paths.get(pathFiles.concat("/classes")))
                .filter(f -> f.toString().endsWith(".mp4"))
                .filter(f -> isIdEquals(classId, f))
                .findFirst()
                .get();
        return Files.readAllBytes(path);
    }

}

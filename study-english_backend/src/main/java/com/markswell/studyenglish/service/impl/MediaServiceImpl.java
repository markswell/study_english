package com.markswell.studyenglish.service.impl;

import org.springframework.stereotype.Service;
import com.markswell.studyenglish.service.MediaService;
import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import static java.nio.file.Files.list;
import static com.markswell.studyenglish.util.BookPathUtil.getBooks;

@Service
public class MediaServiceImpl implements MediaService {

    @Value("${media.context-path}")
    private String pathFiles;

    @Override
    public byte[] getPdf(String book) {
        try {
            Path first = getBooks(pathFiles).filter(f -> f.toString().contains(book)).findFirst().get();
            Path path = list(first).filter(f -> f.toString().endsWith(".pdf")).toList().get(0);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public byte[] getAudio(Long bookId, Long lessonId, String audioId) {
        try {

            Path path = getBook(bookId.toString(), lessonId.toString());
            String audioName = list(path)
                    .map(f -> f.getFileName().toString())
                    .filter(f -> audioId.equals(Long.parseLong(f.substring(f.length() - 6).replace(".mp3", ""))))
                    .findFirst()
                    .get();
            byte[] bytes = Files.readAllBytes(Path.of(path.toString().concat("/").concat(audioName)));
            return bytes;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public byte[] getVideo(String classId) {
        try {
            Path path = list(Paths.get(pathFiles.concat("/classes")))
                    .filter(f -> f.toString().endsWith(".mp4"))
                    .filter(f -> isIdEquals(classId, f))
                    .findFirst()
                    .get();
            return Files.readAllBytes(path);
        } catch (IOException e) {
            return null;
        }
    }

    private Path getBook(String bookId, String lessonId) throws IOException {
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

    private static boolean isIdEquals(String bookId, Path f) {
        return bookId.equals(Long.parseLong(f.getFileName().toString().split("_")[0]));
    }

}

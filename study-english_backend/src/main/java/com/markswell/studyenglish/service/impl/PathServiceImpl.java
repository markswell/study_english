package com.markswell.studyenglish.service.impl;

import org.springframework.stereotype.Service;
import com.markswell.studyenglish.dto.BookDto;
import com.markswell.studyenglish.dto.AudioDto;
import com.markswell.studyenglish.dto.VideoDto;
import com.markswell.studyenglish.dto.LessonDto;
import com.markswell.studyenglish.util.BookPathUtil;
import com.markswell.studyenglish.service.PathService;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.nio.file.Path;
import java.io.IOException;

import static java.nio.file.Files.list;

@Service
public class PathServiceImpl implements PathService {

    @Value("${media.context-path}")
    private String pathFiles;

    @Override
    public List<BookDto> getBooks() throws IOException {
        return BookPathUtil.getBooks(pathFiles)
                .map(this::createBookDtoByPath).toList();
    }

    @Override
    public List<VideoDto> getVideos() {
        try {
            return list(Path.of(pathFiles.concat("/classes")))
                    .map(f -> f.getFileName().toString())
                    .filter(f -> f.endsWith(".mp4"))
                    .sorted()
                    .map(this::convertVideoDto)
                    .toList();
        } catch (IOException e) {
            return null;
        }
    }

    private VideoDto convertVideoDto(String fileName) {
        var split = fileName.replace(".mp4", "").split("_");
        Long id = Long.parseLong(split[0]);
        String url = "/media/class/%d".formatted(id);
        String name = split[1].concat(" ").concat(split[2]);
        return new VideoDto(id, url, name);
    }

    private BookDto createBookDtoByPath(Path path) {
        var bookName = convertBookName(path.getFileName().toString());
        var bookId = convertBookId(path.getFileName().toString());
        var bookPdf = convertBookPdf(path);
        var lessons = convertLessons(path);
        return new BookDto(bookId, bookPdf, bookName, lessons);
    }

    private String convertBookName(String fileName) {
        String[] split = fileName.split("_");
        return split[1].concat(" ").concat(split[2]);
    }

    private Long convertBookId(String fileName) {
        return Long.parseLong(fileName.split("_")[0]);
    }

    private String convertBookPdf(Path fileName) {
        try {
            Path pdf = list(fileName).filter(f -> f.toString().endsWith(".pdf")).findFirst().get();
            return "/study_english/media/".concat(pdf.getFileName().toString().replace(".pdf", ""));
        } catch (IOException e) {
            return null;
        }
    }

    private List<LessonDto> convertLessons(Path path) {
        try {
            return list(path).sorted()
                    .filter(f -> f.toString().contains("lesson"))
                    .map(this::convertLesson).toList();

        } catch(IOException e) {
            return null;
        }
    }

    private LessonDto convertLesson(Path path) {
        try {
            var audioDtos = list(path).sorted().map(this::convertAudio).toList();
            var splitName = path.getFileName().toString().split("_");
            var fileName = splitName[1].concat(" ").concat(splitName[2]);
            var id = Long.parseLong(splitName[0]);
            return new LessonDto(id, fileName, audioDtos);
        } catch (IOException e) {
            return null;
        }
    }

    private AudioDto convertAudio(Path path) {
        String[] strings = path.toString().split("/");
        String bookId = strings[strings.length - 3].substring(0, 1);
        Integer lessonId = Integer.parseInt(strings[strings.length - 2].split("_")[0]);
        String audioName = path.getFileName().toString();
        String id = audioName.substring(audioName.replace(".mp3", "").length() - 2).replace(".mp3", "");
        String url = "/media/%s/%s/%s".formatted(bookId, lessonId, Integer.parseInt(id));
        String title = audioName.replace(".mp3", "");
        String cover = "";
        return new AudioDto(Long.parseLong(id), url, title, audioName);
    }





}

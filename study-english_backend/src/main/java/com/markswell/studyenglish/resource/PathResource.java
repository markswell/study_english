package com.markswell.studyenglish.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.markswell.studyenglish.dto.ListVideoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.markswell.studyenglish.dto.BookDto;
import org.springframework.http.ResponseEntity;
import com.markswell.studyenglish.dto.VideoDto;
import com.markswell.studyenglish.service.PathService;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/path")
@RequiredArgsConstructor
@Tag(name = "Path", description = "It solves paths to medias of course")
public class PathResource {

    private final PathService pathService;

    @CrossOrigin("*")
    @GetMapping(value = "/book", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Book paths",
            description = "It solves paths URL to access audio lessons and PDF's books")
    public ResponseEntity<List<BookDto>> getBooks() throws IOException {
        return ResponseEntity.ok(pathService.getBooks());
    }

    @CrossOrigin("*")
    @GetMapping(value = "/video", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Video paths",
            description = "It solves paths URL to access video classes")
    public ResponseEntity<List<VideoDto>> getVideos() throws IOException {
        return ResponseEntity.ok(pathService.getVideos());
    }

}

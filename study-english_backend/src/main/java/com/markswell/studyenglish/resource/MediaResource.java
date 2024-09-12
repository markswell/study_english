package com.markswell.studyenglish.resource;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.markswell.studyenglish.service.MediaService;

import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
@Tag(name = "Resource", description = "It solves media's data")
public class MediaResource {

    private final MediaService mediaService;

    @GetMapping(value = "/{book}", produces = APPLICATION_PDF_VALUE)
    @Operation(
            summary = "Book",
            description = "It solves PDF's book file"
    )
    public ResponseEntity<byte[]> getBookPdf(
            @Parameter(description = "It is the book name for searching")
            @PathVariable("book") String book) {
        return ResponseEntity.ok(mediaService.getPdf(book));
    }

    @GetMapping(value = "/{book}/{lessonId}/pdf", produces = APPLICATION_PDF_VALUE)
    @Operation(
            summary = "Book",
            description = "It solves PDF's book file"
    )
    public ResponseEntity<byte[]> getBookPdfByLesson(
            @Parameter(description = "It is the book name for searching")
            @PathVariable("book") Long book,
            @Parameter(description = "It is the lesson number to search")
            @PathVariable("lessonId") Long lessonId) {
        return ResponseEntity.ok(mediaService.getPdfByLesson(book, lessonId));
    }

    @GetMapping(value = "/{bookId}/{lessonId}/{audioId}", produces = APPLICATION_OCTET_STREAM_VALUE)
    @Operation(
            summary = "Audio",
            description = "It solves audio's lesson file"
    )
    public ResponseEntity<byte[]> getAudio(
            @Parameter(description = "It is the book id for searching")
            @PathVariable("bookId") Long bookId,
            @Parameter(description = "It is the lesson id for searching")
            @PathVariable("lessonId") Long lessonId,
            @Parameter(description = "It is the audio id for searching")
            @PathVariable("audioId") Long audioId) {
        return ResponseEntity.ok(mediaService.getAudio(bookId, lessonId, audioId));
    }

    @GetMapping(value = "/class/{classId}", produces = APPLICATION_OCTET_STREAM_VALUE)
    @Operation(
            summary = "Video",
            description = "It solves video's class file"
    )
    public ResponseEntity<byte[]> getVideo(
            @Parameter(description = "It is the class id for searching")
            @PathVariable("classId") Long classId) {
        return ResponseEntity.ok(mediaService.getVideo(classId));
    }

    @GetMapping(value = "/class/{classId}/thumbnail", produces = APPLICATION_OCTET_STREAM_VALUE)
    @Operation(
            summary = "Video",
            description = "It solves video's class file"
    )
    public ResponseEntity<byte[]> getThumbnail(
            @Parameter(description = "It is the class id for searching")
            @PathVariable("classId") Long classId) {
        return ResponseEntity.ok(mediaService.getThumbnail(classId));
    }

}

package com.markswell.studyenglish.resource;

import com.markswell.studyenglish.service.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
@Tag(name = "Resource", description = "It solves media's data")
public class MediaResource {

    private final MediaService mediaService;

    @CrossOrigin("*")
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

    @CrossOrigin("*")
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
            @PathVariable("audioId") String audioId) {
        return ResponseEntity.ok(mediaService.getAudio(bookId, lessonId, audioId));
    }

    @CrossOrigin("*")
    @GetMapping(value = "/class/{classId}", produces = APPLICATION_OCTET_STREAM_VALUE)
    @Operation(
            summary = "Video",
            description = "It solves video's class file"
    )
    public ResponseEntity<byte[]> getVideo(
            @Parameter(description = "It is the class id for searching")
            @PathVariable("classId") String classId) {
        return ResponseEntity.ok(mediaService.getVideo(classId));
    }

}

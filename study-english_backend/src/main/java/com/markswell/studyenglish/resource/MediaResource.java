package com.markswell.studyenglish.resource;

import com.markswell.studyenglish.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class MediaResource {

    private final MediaService mediaService;

    @CrossOrigin("*")
    @GetMapping(value = "/{book}", produces = APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getBookPdf(@PathVariable("book") String book) {
        return ResponseEntity.ok(mediaService.getPdf(book));
    }

    @CrossOrigin("*")
    @GetMapping(value = "/{bookId}/{lessonId}/{audioId}", produces = APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getAudio(@PathVariable("bookId") Long bookId,
                                           @PathVariable("lessonId") Long lessonId,
                                           @PathVariable("audioId") Long audioId) {
        return ResponseEntity.ok(mediaService.getAudio(bookId, lessonId, audioId));
    }

    @CrossOrigin("*")
    @GetMapping(value = "/class/{classId}", produces = APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getVideo(@PathVariable("classId") Long classId) {
        return ResponseEntity.ok(mediaService.getVideo(classId));
    }

}

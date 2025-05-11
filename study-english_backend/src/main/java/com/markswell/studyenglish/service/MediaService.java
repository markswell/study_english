package com.markswell.studyenglish.service;

import org.springframework.http.ResponseEntity;

public interface MediaService {

    byte[] getPdf(String book);

    byte[] getPdfByLesson(Long book, Long lessonId);

    ResponseEntity<byte[]> getAudioByRange(Long bookId, Long lessonId, Long audioId, String rangeHeader);

    ResponseEntity<byte[]> getVideoByRage(Long classId, String rangeHeader);

    byte[] getThumbnail(Long classId);
}

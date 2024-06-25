package com.markswell.studyenglish.service;

public interface MediaService {

    byte[] getPdf(String book);

    byte[] getAudio(Long bookId, Long lessonId, Long audioId);

    byte[] getVideo(Long classId);

    byte[] getThumbnail(Long classId);
}

package com.markswell.studyenglish.service;

public interface MediaService {

    byte[] getPdf(String book);

    byte[] getAudio(Long bookId, Long lessonId,  String audioId);

    byte[] getVideo(String classId);
}

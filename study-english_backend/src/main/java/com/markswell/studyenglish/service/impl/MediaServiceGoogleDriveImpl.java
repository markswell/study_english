package com.markswell.studyenglish.service.impl;

import com.markswell.studyenglish.service.GoogleAccessService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Primary;
import com.markswell.studyenglish.service.MediaService;

@Service
@Primary
@AllArgsConstructor
public class MediaServiceGoogleDriveImpl implements MediaService {

    private final GoogleAccessService service;

    @Override
    public byte[] getPdf(String book) {
        return getFileBytes(book);
    }

    @Override
    public byte[] getAudio(Long bookId, Long lessonId, String audioId) {
        return getFileBytes(audioId);
    }

    @Override
    public byte[] getVideo(String classId) {
        return new byte[0];
    }

    private byte[] getFileBytes(String fileId) {
        try {
            return service.getFile(fileId);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}

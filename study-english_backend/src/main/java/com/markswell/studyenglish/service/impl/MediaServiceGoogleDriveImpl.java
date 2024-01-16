package com.markswell.studyenglish.service.impl;

import com.markswell.studyenglish.service.GoogleAccessService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Primary;
import com.markswell.studyenglish.service.MediaService;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.io.InputStreamReader;

@Service
@Primary
@AllArgsConstructor
public class MediaServiceGoogleDriveImpl implements MediaService {

    private final GoogleAccessService service;

    @Override
    public byte[] getPdf(String book) {
        try(InputStream input = getClass().getResourceAsStream("/%s.pdf".formatted(book))) {
            return FileCopyUtils.copyToByteArray(input);

        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public byte[] getAudio(Long bookId, Long lessonId, String audioId) {
        return getFileBytes(audioId);
    }

    @Override
    public byte[] getVideo(String classId) {
        return getFileBytes(classId);
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

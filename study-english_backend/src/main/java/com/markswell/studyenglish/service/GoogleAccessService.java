package com.markswell.studyenglish.service;

import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GoogleAccessService {
    String getFilesNames() throws IOException, GeneralSecurityException;

    byte[] getFile(@PathVariable("realFileId") String realFileId) throws IOException, GeneralSecurityException;
}

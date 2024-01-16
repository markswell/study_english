package com.markswell.studyenglish.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import com.markswell.studyenglish.service.GoogleAccessService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/names")
@RequiredArgsConstructor
public class MeExclua {

    private final GoogleAccessService service;

    @GetMapping
    public ResponseEntity<byte[]> getNames() throws GeneralSecurityException, IOException {
        try(OutputStream outputStream = new FileOutputStream("/home/markswell/books.txt");
            OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {
            String filesNames = service.getFilesNames();
            writer.write(filesNames);
            return ResponseEntity.ok(filesNames.getBytes());

        }catch (Exception e){
            throw e;
        }
    }

}

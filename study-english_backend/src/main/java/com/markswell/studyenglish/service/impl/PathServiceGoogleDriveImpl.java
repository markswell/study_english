package com.markswell.studyenglish.service.impl;

import com.google.gson.Gson;
import com.markswell.studyenglish.dto.BookDto;
import com.markswell.studyenglish.dto.ListBookDto;
import com.markswell.studyenglish.dto.ListVideoDto;
import com.markswell.studyenglish.dto.VideoDto;
import com.markswell.studyenglish.service.PathService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PathServiceGoogleDriveImpl implements PathService {

    private final Gson gson;

    @Override
    public List<BookDto> getBooks() throws IOException {
        var listBookDto = gson.fromJson(getJson("/book_path.json"), ListBookDto.class);
        return listBookDto.getListBookDto();
    }

    @Override
    public List<VideoDto> getVideos() {
        var listVideoDto = gson.fromJson(getJson("/video_path.json"), ListVideoDto.class);
        return listVideoDto.getVideoDtoList();
    }

    private String getJson(String path) {
        try(InputStream input = getClass().getResourceAsStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(input)) {
            return FileCopyUtils.copyToString(inputStreamReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

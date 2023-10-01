package com.markswell.studyenglish.service;

import com.markswell.studyenglish.dto.BookDto;
import com.markswell.studyenglish.dto.VideoDto;

import java.io.IOException;
import java.util.List;

public interface PathService {
    List<BookDto> getBooks() throws IOException;

    List<VideoDto> getVideos();
}

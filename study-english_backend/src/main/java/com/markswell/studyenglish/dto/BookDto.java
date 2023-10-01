package com.markswell.studyenglish.dto;

import java.util.List;

public record BookDto(Long id, String pdf, String book, List<LessonDto> lessons) {}

package com.markswell.studyenglish.dto;

import java.util.List;

public record LessonDto(Long id, String lesson, List<AudioDto> audios) { }

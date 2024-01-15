package com.markswell.studyenglish.dto;

import lombok.Data;

import java.util.List;
import java.io.Serializable;

@Data
public class ListVideoDto implements Serializable {

    private List<VideoDto> videoDtoList;

}

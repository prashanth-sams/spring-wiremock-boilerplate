package com.wmock.info.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Chapter6RequestDTO implements Serializable {

    private int chapterId;
    private String name;
}

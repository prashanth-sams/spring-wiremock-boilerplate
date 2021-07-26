package com.wmock.info.models;

public class ChapterInfoModel {

    private String chapterId;
    private String name;

    public ChapterInfoModel(String chapterId, String name) {
        this.chapterId = chapterId;
        this.name = name;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

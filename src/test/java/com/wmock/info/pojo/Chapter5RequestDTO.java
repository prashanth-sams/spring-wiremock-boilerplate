package com.wmock.info.pojo;

public class Chapter5RequestDTO {

    public int chapterId;
    public String name;

    public Chapter5RequestDTO() {
    }

    /* use setters if needed - optional*/
    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "chapterId=" + chapterId +
                ", name='" + name + '\'' +
                '}';
    }
}

package com.wmock.info.pojo;

public class Chapter4RequestDTO {

    public int chapterId;
    public String name;

    /**
     *
     * @param chapterId
     * @param name
     */
    public Chapter4RequestDTO(int chapterId, String name) {
        this.chapterId = chapterId;
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

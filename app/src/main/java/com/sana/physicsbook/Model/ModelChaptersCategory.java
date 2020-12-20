package com.sana.physicsbook.Model;

public class ModelChaptersCategory {
    String ChapterNumber, ChapterTitle;

    public ModelChaptersCategory() {
    }

    public ModelChaptersCategory(String chapterNumber, String chapterTitle) {
        ChapterNumber = chapterNumber;
        ChapterTitle = chapterTitle;
    }

    public String getChapterNumber() {
        return ChapterNumber;
    }

    public void setChapterNumber(String chapterNumber) {
        ChapterNumber = chapterNumber;
    }

    public String getChapterTitle() {
        return ChapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        ChapterTitle = chapterTitle;
    }
}

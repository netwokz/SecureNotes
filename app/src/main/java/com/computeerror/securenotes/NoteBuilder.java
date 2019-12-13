package com.computeerror.securenotes;

public class NoteBuilder {

    private String mTitle;
    private String mContent;

    public NoteBuilder() {

    }

    public NoteBuilder(String title, String content) {
        this.mTitle = title;
        this.mContent = content;
    }

    public String title() {
        return mTitle;
    }

    public String content() {
        return mContent;
    }

}

package com.example.mywechat.data;

public class Comment {
    private String Speaker;
    private String ReplyTo;
    private String Content;

    public Comment(String Speaker, String ReplyTo, String Content) {
        this.Speaker = Speaker;
        this.ReplyTo = ReplyTo;
        this.Content = Content;
    }

    public String Show() {
        return Speaker+" 回复 "+ReplyTo+": "+Content;
    }
}

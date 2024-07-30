package com.beans;

import java.time.LocalDate;

public class Comments {
    private String post_id;
    private int comment_id;
    private String comment;
    private LocalDate comment_date;

    public Comments() {
    }

    public Comments(int comment_id, String comment, LocalDate comment_date) {
        this.comment_id = comment_id;
        this.comment = comment;
        this.comment_date = comment_date;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getComment_date() {
        return comment_date;
    }

    public void setComment_date(LocalDate comment_date) {
        this.comment_date = comment_date;
    }
}

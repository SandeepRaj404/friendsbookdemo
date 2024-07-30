package com.beans;

public class Likes {
    private int likeId;
    private String postId;
    private int start_date;


    public Likes() {
    }

    public Likes(int likeId, String postId, int start_date) {
        this.likeId = likeId;
        this.postId = postId;
        this.start_date = start_date;
    }

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public int getStart_date() {
        return start_date;
    }

    public void setStart_date(int start_date) {
        this.start_date = start_date;
    }
}

package com.response.beans;

public class LikesLoadResponse {
    private String error;
    private String message;
    private String status;
    private String data;

    public LikesLoadResponse() {
    }

    public LikesLoadResponse(String error, String message, String status, String data) {
        this.error = error;
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

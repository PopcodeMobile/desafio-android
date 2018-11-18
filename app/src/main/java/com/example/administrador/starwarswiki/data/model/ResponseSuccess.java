package com.example.administrador.starwarswiki.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseSuccess {
    private String status;
    private String message;

    public ResponseSuccess(){
        status = null;
        message = null;
    }

    public ResponseSuccess(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

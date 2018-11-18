package com.example.administrador.starwarswiki.data.model;

public class ResponseError {
    private String error;
    private String error_message;

    public ResponseError(){
        error = null;
        error_message = null;
    }

    public ResponseError(String error, String error_message) {
        this.error = error;
        this.error_message = error_message;
    }

    public String geterror() {
        return error;
    }

    public void seterror(String error) {
        this.error = error;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}

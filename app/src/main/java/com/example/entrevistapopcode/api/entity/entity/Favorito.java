package com.example.entrevistapopcode.api.entity.entity;

import com.google.gson.annotations.SerializedName;

public class Favorito {

    @SerializedName("error")
    private String error;

    @SerializedName("error_message")
    private String errorMessage;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    public String getError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

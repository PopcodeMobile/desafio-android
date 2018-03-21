package com.example.lucvaladao.entrevistapopcode.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by lucvaladao on 3/20/18.
 */

public class FavResponse implements Serializable{

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

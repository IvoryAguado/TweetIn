package com.example.mrx.twitterapp.api.apicore;


import com.example.mrx.twitterapp.BuildConfig;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class RestError {

    @SerializedName("code")
    public Integer code;
    @SerializedName("error_message")
    public String error_message;
    @SerializedName("detail")
    public String strMessageDetail;

    public RestError() {
    }

    public RestError(String strMessage) {
        this.error_message = strMessage;
    }

    public String getStrMessageDetail() {
        StringBuilder stringBuilder = new StringBuilder();
        if (strMessageDetail != null )
            stringBuilder.append(strMessageDetail);
        if (error_message != null && BuildConfig.DEBUG)
            stringBuilder.append("\nError: " + error_message);
        return stringBuilder.toString().replaceAll("\\[", "").replaceAll("]", "");
    }

    public Integer getCode() {
        return code;
    }
}
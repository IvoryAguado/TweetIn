package com.example.mrx.twitterapp.api.apicore;

import com.google.gson.annotations.SerializedName;
import com.innovacorp.profound.BuildConfig;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class RestError {

    @SerializedName("code")
    public Integer code;

    @SerializedName("error_message")
    public String error_message;

    @SerializedName("detail")
    public String strMessageDetail;

    @SerializedName("username")
    public List<String> username_help;

    @SerializedName("email")
    public List<String> email_help;

    public RestError(String strMessage) {
        this.error_message = strMessage;
    }

    public String getStrMessageDetail() {
        StringBuilder stringBuilder = new StringBuilder();
        if (strMessageDetail != null )
            stringBuilder.append(strMessageDetail);
        if (error_message != null && BuildConfig.DEBUG)
            stringBuilder.append("\nError: " + error_message);
        if (username_help != null)
            stringBuilder.append("\nName: " + username_help);
        if (email_help != null)
            stringBuilder.append("\nEmail: " + email_help);
        return stringBuilder.toString().replaceAll("\\[", "").replaceAll("]", "");
    }

    public Integer getCode() {
        return code;
    }
}
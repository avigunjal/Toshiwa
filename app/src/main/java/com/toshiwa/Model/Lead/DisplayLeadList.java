package com.toshiwa.Model.Lead;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DisplayLeadList {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<DisplayLeadListResult> result = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DisplayLeadListResult> getResult() {
        return result;
    }

    public void setResult(List<DisplayLeadListResult> result) {
        this.result = result;
    }
}

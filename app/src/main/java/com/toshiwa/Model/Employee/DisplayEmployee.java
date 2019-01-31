package com.toshiwa.Model.Employee;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DisplayEmployee {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<DisplayEmployeeResult> result = null;

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

    public List<DisplayEmployeeResult> getResult() {
        return result;
    }

    public void setResult(List<DisplayEmployeeResult> result) {
        this.result = result;
    }
}

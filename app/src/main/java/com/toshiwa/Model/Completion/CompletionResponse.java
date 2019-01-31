package com.toshiwa.Model.Completion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompletionResponse {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private CompletionResult completionResult;

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

    public CompletionResult getCompletionResult() {
        return completionResult;
    }

    public void setCompletionResult(CompletionResult result) {
        this.completionResult = result;
    }
}

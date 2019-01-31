package com.toshiwa.Model.Approval;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApprovalResponse {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private ApprovalResult approvalResult;

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

    public ApprovalResult getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(ApprovalResult purchaseResult) {
        this.approvalResult = purchaseResult;
    }
}

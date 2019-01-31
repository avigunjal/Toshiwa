package com.toshiwa.Model.Bill;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillResponse {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private BillResult billResult;

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

    public BillResult getBillResult() {
        return billResult;
    }

    public void setBillResult(BillResult result) {
        this.billResult = result;
    }
}

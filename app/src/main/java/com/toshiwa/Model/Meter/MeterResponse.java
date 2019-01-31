package com.toshiwa.Model.Meter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeterResponse {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private MeterResult meterResult;

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

    public MeterResult getMeterResult() {
        return meterResult;
    }

    public void setMeterResult(MeterResult meterResult) {
        this.meterResult = meterResult;
    }
}

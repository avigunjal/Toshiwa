package com.toshiwa.Model.Material;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaterialResponse {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private MaterialResult materialResult;

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

    public MaterialResult getMaterialResult() {
        return materialResult;
    }

    public void setMaterialResult(MaterialResult materialResult) {
        this.materialResult = materialResult;
    }
}

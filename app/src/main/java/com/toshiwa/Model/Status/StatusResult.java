package com.toshiwa.Model.Status;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusResult {
    @SerializedName("lead")
    @Expose
    private String lead;
    @SerializedName("purchase")
    @Expose
    private String purchase;
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("approval")
    @Expose
    private String approval;
    @SerializedName("bill")
    @Expose
    private String bill;
    @SerializedName("completion")
    @Expose
    private String completion;
    @SerializedName("execution")
    @Expose
    private String execution;
    @SerializedName("material")
    @Expose
    private String material;
    @SerializedName("meter_reading")
    @Expose
    private String meterReading;
    @SerializedName("service")
    @Expose
    private String service;

    public String getLead() {
        return lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    public String getExecution() {
        return execution;
    }

    public void setExecution(String execution) {
        this.execution = execution;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(String meterReading) {
        this.meterReading = meterReading;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

}

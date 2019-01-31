package com.toshiwa.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountResult {

   @SerializedName("accid")
    @Expose
    private String accid;
    @SerializedName("lid")
    @Expose
    private String lid;
    @SerializedName("empid")
    @Expose
    private String empid;
    @SerializedName("acc_count")
    @Expose
    private String accCount;
    @SerializedName("po_amount")
    @Expose
    private String poAmount;
    @SerializedName("advance_received")
    @Expose
    private String advanceReceived;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getAccCount() {
        return accCount;
    }

    public void setAccCount(String accCount) {
        this.accCount = accCount;
    }

    public String getPoAmount() {
        return poAmount;
    }

    public void setPoAmount(String poAmount) {
        this.poAmount = poAmount;
    }

    public String getAdvanceReceived() {
        return advanceReceived;
    }

    public void setAdvanceReceived(String advanceReceived) {
        this.advanceReceived = advanceReceived;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }}
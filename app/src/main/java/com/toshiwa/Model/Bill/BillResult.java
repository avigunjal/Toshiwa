package com.toshiwa.Model.Bill;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillResult {

    @SerializedName("billid")
    @Expose
    private String billid;
    @SerializedName("lid")
    @Expose
    private String lid;
    @SerializedName("empid")
    @Expose
    private String empid;
    @SerializedName("responsible_person")
    @Expose
    private String responsiblePerson;
    @SerializedName("meter_installation_date")
    @Expose
    private String meterInstallationDate;
    @SerializedName("solar_tagged")
    @Expose
    private String solarTagged;
    @SerializedName("solar_tagged_date")
    @Expose
    private String solarTaggedDate;
    @SerializedName("first_bill_received")
    @Expose
    private String firstBillReceived;
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

    public String getBillid() {
        return billid;
    }

    public void setBillid(String billid) {
        this.billid = billid;
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

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getMeterInstallationDate() {
        return meterInstallationDate;
    }

    public void setMeterInstallationDate(String meterInstallationDate) {
        this.meterInstallationDate = meterInstallationDate;
    }

    public String getSolarTagged() {
        return solarTagged;
    }

    public void setSolarTagged(String solarTagged) {
        this.solarTagged = solarTagged;
    }

    public String getSolarTaggedDate() {
        return solarTaggedDate;
    }

    public void setSolarTaggedDate(String solarTaggedDate) {
        this.solarTaggedDate = solarTaggedDate;
    }

    public String getFirstBillReceived() {
        return firstBillReceived;
    }

    public void setFirstBillReceived(String firstBillReceived) {
        this.firstBillReceived = firstBillReceived;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
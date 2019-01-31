package com.toshiwa.Model.Service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceResult {


    @SerializedName("serviceid")
    @Expose
    private String serviceid;
    @SerializedName("lid")
    @Expose
    private String lid;
    @SerializedName("empid")
    @Expose
    private String empid;
    @SerializedName("responsible_person")
    @Expose
    private String responsiblePerson;
    @SerializedName("scheduled_date")
    @Expose
    private String scheduledDate;
    @SerializedName("call_count")
    @Expose
    private String callCount;
    @SerializedName("call_date")
    @Expose
    private String callDate;
    @SerializedName("call_remark")
    @Expose
    private String callRemark;
    @SerializedName("call_attend_remark")
    @Expose
    private String callAttendRemark;

    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
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

    public String getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getCallCount() {
        return callCount;
    }

    public void setCallCount(String callCount) {
        this.callCount = callCount;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getCallRemark() {
        return callRemark;
    }

    public void setCallRemark(String callRemark) {
        this.callRemark = callRemark;
    }

    public String getCallAttendRemark() {
        return callAttendRemark;
    }

    public void setCallAttendRemark(String callAttendRemark) {
        this.callAttendRemark = callAttendRemark;
    }
}
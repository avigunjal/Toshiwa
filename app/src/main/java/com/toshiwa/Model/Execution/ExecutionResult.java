package com.toshiwa.Model.Execution;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExecutionResult {

    @SerializedName("exid")
    @Expose
    private String exid;
    @SerializedName("lid")
    @Expose
    private String lid;
    @SerializedName("empid")
    @Expose
    private String empid;
    @SerializedName("responsible_person")
    @Expose
    private String responsiblePerson;
    @SerializedName("material_ready")
    @Expose
    private String materialReady;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("work_completion_date")
    @Expose
    private String workCompletionDate;
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

    public String getExid() {
        return exid;
    }

    public void setExid(String exid) {
        this.exid = exid;
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

    public String getMaterialReady() {
        return materialReady;
    }

    public void setMaterialReady(String materialReady) {
        this.materialReady = materialReady;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getWorkCompletionDate() {
        return workCompletionDate;
    }

    public void setWorkCompletionDate(String workCompletionDate) {
        this.workCompletionDate = workCompletionDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
package com.toshiwa.Model.Approval;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApprovalResult {
    @SerializedName("aid")
    @Expose
    private String aid;
    @SerializedName("lid")
    @Expose
    private String lid;
    @SerializedName("empid")
    @Expose
    private String empid;
    @SerializedName("responsible_person")
    @Expose
    private String responsiblePerson;
    @SerializedName("approval_date")
    @Expose
    private String approvalDate;
    @SerializedName("load_extension_done")
    @Expose
    private String loadExtensionDone;
    @SerializedName("quotation_paid")
    @Expose
    private String quotationPaid;
    @SerializedName("load_reflection_bill")
    @Expose
    private String loadReflectionBill;
    @SerializedName("solar_online_application")
    @Expose
    private String solarOnlineApplication;
    @SerializedName("solar_offline_application")
    @Expose
    private String solarOfflineApplication;
    @SerializedName("solar_sanction_received")
    @Expose
    private String solarSanctionReceived;
    @SerializedName("meda_application_done")
    @Expose
    private String medaApplicationDone;
    @SerializedName("meda_sanction_received")
    @Expose
    private String medaSanctionReceived;
    @SerializedName("net_meter_no")
    @Expose
    private String netMeterNo;
    @SerializedName("generation_meter_no")
    @Expose
    private String generationMeterNo;
    @SerializedName("joint_inspection")
    @Expose
    private String jointInspection;
    @SerializedName("pcr_entered")
    @Expose
    private String pcrEntered;
    @SerializedName("funds_released")
    @Expose
    private String fundsReleased;
    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
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

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getLoadExtensionDone() {
        return loadExtensionDone;
    }

    public void setLoadExtensionDone(String loadExtensionDone) {
        this.loadExtensionDone = loadExtensionDone;
    }

    public String getQuotationPaid() {
        return quotationPaid;
    }

    public void setQuotationPaid(String quotationPaid) {
        this.quotationPaid = quotationPaid;
    }

    public String getLoadReflectionBill() {
        return loadReflectionBill;
    }

    public void setLoadReflectionBill(String loadReflectionBill) {
        this.loadReflectionBill = loadReflectionBill;
    }

    public String getSolarOnlineApplication() {
        return solarOnlineApplication;
    }

    public void setSolarOnlineApplication(String solarOnlineApplication) {
        this.solarOnlineApplication = solarOnlineApplication;
    }

    public String getSolarOfflineApplication() {
        return solarOfflineApplication;
    }

    public void setSolarOfflineApplication(String solarOfflineApplication) {
        this.solarOfflineApplication = solarOfflineApplication;
    }

    public String getSolarSanctionReceived() {
        return solarSanctionReceived;
    }

    public void setSolarSanctionReceived(String solarSanctionReceived) {
        this.solarSanctionReceived = solarSanctionReceived;
    }

    public String getMedaApplicationDone() {
        return medaApplicationDone;
    }

    public void setMedaApplicationDone(String medaApplicationDone) {
        this.medaApplicationDone = medaApplicationDone;
    }

    public String getMedaSanctionReceived() {
        return medaSanctionReceived;
    }

    public void setMedaSanctionReceived(String medaSanctionReceived) {
        this.medaSanctionReceived = medaSanctionReceived;
    }

    public String getNetMeterNo() {
        return netMeterNo;
    }

    public void setNetMeterNo(String netMeterNo) {
        this.netMeterNo = netMeterNo;
    }

    public String getGenerationMeterNo() {
        return generationMeterNo;
    }

    public void setGenerationMeterNo(String generationMeterNo) {
        this.generationMeterNo = generationMeterNo;
    }

    public String getJointInspection() {
        return jointInspection;
    }

    public void setJointInspection(String jointInspection) {
        this.jointInspection = jointInspection;
    }

    public String getPcrEntered() {
        return pcrEntered;
    }

    public void setPcrEntered(String pcrEntered) {
        this.pcrEntered = pcrEntered;
    }

    public String getFundsReleased() {
        return fundsReleased;
    }

    public void setFundsReleased(String fundsReleased) {
        this.fundsReleased = fundsReleased;
    }}

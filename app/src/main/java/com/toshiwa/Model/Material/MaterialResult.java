package com.toshiwa.Model.Material;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaterialResult {

    @SerializedName("mid")
    @Expose
    private String mid;
    @SerializedName("lid")
    @Expose
    private String lid;
    @SerializedName("empid")
    @Expose
    private String empid;
    @SerializedName("responsible_person")
    @Expose
    private String responsiblePerson;
    @SerializedName("inverter")
    @Expose
    private String inverter;
    @SerializedName("module")
    @Expose
    private String module;
    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("order_details")
    @Expose
    private String orderDetails;
    @SerializedName("available")
    @Expose
    private String available;
    @SerializedName("available_details")
    @Expose
    private String availableDetails;
    @SerializedName("material_dispatched_onsite")
    @Expose
    private String materialDispatchedOnsite;
    @SerializedName("dispatched_date")
    @Expose
    private String dispatchedDate;
    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
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

    public String getInverter() {
        return inverter;
    }

    public void setInverter(String inverter) {
        this.inverter = inverter;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getAvailableDetails() {
        return availableDetails;
    }

    public void setAvailableDetails(String availableDetails) {
        this.availableDetails = availableDetails;
    }

    public String getMaterialDispatchedOnsite() {
        return materialDispatchedOnsite;
    }

    public void setMaterialDispatchedOnsite(String materialDispatchedOnsite) {
        this.materialDispatchedOnsite = materialDispatchedOnsite;
    }

    public String getDispatchedDate() {
        return dispatchedDate;
    }

    public void setDispatchedDate(String dispatchedDate) {
        this.dispatchedDate = dispatchedDate;
    }

}
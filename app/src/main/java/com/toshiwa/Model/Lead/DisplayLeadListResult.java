package com.toshiwa.Model.Lead;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DisplayLeadListResult {
    @SerializedName("lid")
    @Expose
    private String lid;

   @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("delete")
    @Expose
    private String delete;

    @SerializedName("dealer")
    @Expose
    private String dealer;

    @SerializedName("capacity")
    @Expose
    private String capacity;

    @SerializedName("contact_person")
    @Expose
    private String contact_person;

    @SerializedName("capex")
    @Expose
    private String capex;

    @SerializedName("opex")
    @Expose
    private String opex;

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getCapex() {
        return capex;
    }

    public void setCapex(String capex) {
        this.capex = capex;
    }

    public String getOpex() {
        return opex;
    }

    public void setOpex(String opex) {
        this.opex = opex;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}

package com.toshiwa.Model.Purchase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchaseResult {
    @SerializedName("empid")
    @Expose
    private String empid;
    @SerializedName("lid")
    @Expose
    private String lid;
    @SerializedName("responsible_person")
    @Expose
    private String responsiblePerson;
    @SerializedName("purchase_date")
    @Expose
    private String purchaseDate;
    @SerializedName("subsidy")
    @Expose
    private String subsidy;
    @SerializedName("grid")
    @Expose
    private String grid;
    @SerializedName("load_extenstion")
    @Expose
    private String loadExtenstion;
    @SerializedName("phase")
    @Expose
    private String phase;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("project_cost")
    @Expose
    private String projectCost;
    @SerializedName("fabrication")
    @Expose
    private String fabrication;
    @SerializedName("invertor")
    @Expose
    private String invertor;
    @SerializedName("panel")
    @Expose
    private String panel;
    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(String subsidy) {
        this.subsidy = subsidy;
    }

    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }

    public String getLoadExtenstion() {
        return loadExtenstion;
    }

    public void setLoadExtenstion(String loadExtenstion) {
        this.loadExtenstion = loadExtenstion;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(String projectCost) {
        this.projectCost = projectCost;
    }

    public String getFabrication() {
        return fabrication;
    }

    public void setFabrication(String fabrication) {
        this.fabrication = fabrication;
    }

    public String getInvertor() {
        return invertor;
    }

    public void setInvertor(String invertor) {
        this.invertor = invertor;
    }

    public String getPanel() {
        return panel;
    }

    public void setPanel(String panel) {
        this.panel = panel;
    }
}

package com.toshiwa.Model.Meter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeterResult {


    @SerializedName("mrid")
    @Expose
    private String mrid;
    @SerializedName("lid")
    @Expose
    private String lid;
    @SerializedName("empid")
    @Expose
    private String empid;
    @SerializedName("reading_count")
    @Expose
    private String readingCount;
    @SerializedName("reader_name")
    @Expose
    private String readerName;
    @SerializedName("reading_date")
    @Expose
    private String readingDate;
    @SerializedName("export")
    @Expose
    private String export;
    @SerializedName("import")
    @Expose
    private String _import;
    @SerializedName("generation")
    @Expose
    private String generation;
    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMrid() {
        return mrid;
    }

    public void setMrid(String mrid) {
        this.mrid = mrid;
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

    public String getReadingCount() {
        return readingCount;
    }

    public void setReadingCount(String readingCount) {
        this.readingCount = readingCount;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getReadingDate() {
        return readingDate;
    }

    public void setReadingDate(String readingDate) {
        this.readingDate = readingDate;
    }

    public String getExport() {
        return export;
    }

    public void setExport(String export) {
        this.export = export;
    }

    public String getImport() {
        return _import;
    }

    public void setImport(String _import) {
        this._import = _import;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }
}
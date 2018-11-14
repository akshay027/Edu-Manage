package com.maxlore.edumanage.Models.ParentModels.ParentTransfercertificate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 8/28/2016.
 */
public class TransferCertificate {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("admission_number")
    @Expose
    private String admissionNumber;
    @SerializedName("standard")
    @Expose
    private String standard;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("parent_name")
    @Expose
    private String parentName;
    @SerializedName("flag")
    @Expose
    private Boolean flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(String admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }


    @Override
    public String toString() {
        return "TransferCertificate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", admissionNumber='" + admissionNumber + '\'' +
                ", standard='" + standard + '\'' +
                ", section='" + section + '\'' +
                ", parentName='" + parentName + '\'' +
                ", flag=" + flag +
                '}';
    }
}
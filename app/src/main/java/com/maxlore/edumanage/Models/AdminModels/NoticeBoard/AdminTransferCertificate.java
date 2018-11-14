package com.maxlore.edumanage.Models.AdminModels.NoticeBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 10-Mar-17.
 */

public class AdminTransferCertificate {
    @SerializedName("student_name")
    @Expose
    private String studentName;
    @SerializedName("admission_number")
    @Expose
    private String admissionNumber;
    @SerializedName("applied_on")
    @Expose
    private String appliedOn;
    @SerializedName("image")
    @Expose
    private String image;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(String admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public String getAppliedOn() {
        return appliedOn;
    }

    public void setAppliedOn(String appliedOn) {
        this.appliedOn = appliedOn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}

package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels;

/**
 * Created by Shrey on 24-Jul-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefaulterDatamodel {

    @SerializedName("student_name")
    @Expose
    private String studentName;
    @SerializedName("student_class")
    @Expose
    private String studentClass;
    @SerializedName("photo")
    @Expose
    private String photo;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "DefaulterDatamodel{" +
                "studentName='" + studentName + '\'' +
                ", studentClass='" + studentClass + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}

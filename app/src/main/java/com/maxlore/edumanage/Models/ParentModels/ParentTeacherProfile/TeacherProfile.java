package com.maxlore.edumanage.Models.ParentModels.ParentTeacherProfile;

/**
 * Created by maxlore on 29-Aug-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeacherProfile {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("subject")
    @Expose
    private List<String> subject;
    @SerializedName("personal_email")
    @Expose
    private String personalEmail;
    @SerializedName("school_email")
    @Expose
    private String schoolEmail;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("image")
    @Expose
    private String photo;
    @SerializedName("class_teacher")
    @Expose
    private Boolean classTeacher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSubject() {
        return subject;
    }

    public void setSubject(List<String> subject) {
        this.subject = subject;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public String getSchoolEmail() {
        return schoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(Boolean classTeacher) {
        this.classTeacher = classTeacher;
    }

    @Override
    public String toString() {
        return "TeacherProfile{" +
                "name='" + name + '\'' +
                ", subject=" + subject +
                ", personalEmail='" + personalEmail + '\'' +
                ", schoolEmail='" + schoolEmail + '\'' +
                ", phone='" + phone + '\'' +
                ", photo='" + photo + '\'' +
                ", classTeacher=" + classTeacher +
                '}';
    }
}
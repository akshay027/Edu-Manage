package com.maxlore.edumanage.Models.ParentModels.ParentTeacherProfile;

/**
 * Created by maxlore on 29-Aug-16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Teacher {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("personal_email")
    @Expose
    private String personalEmail;
    @SerializedName("school_email")
    @Expose
    private String schoolEmail;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("acknowledgement")
    @Expose
    private String acknowledgement;
    @SerializedName("class_teacher")
    @Expose
    private String classTeacher;

    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    public String getAcknowledgement() {
        return acknowledgement;
    }

    public void setAcknowledgement(String acknowledgement) {
        this.acknowledgement = acknowledgement;
    }


    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     *
     * @param subject
     * The subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     *
     * @return
     * The personalEmail
     */
    public String getPersonalEmail() {
        return personalEmail;
    }

    /**
     *
     * @param personalEmail
     * The personal_email
     */
    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    /**
     *
     * @return
     * The schoolEmail
     */
    public String getSchoolEmail() {
        return schoolEmail;
    }

    /**
     *
     * @param schoolEmail
     * The school_email
     */
    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

    /**
     *
     * @return
     * The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", personalEmail='" + personalEmail + '\'' +
                ", schoolEmail='" + schoolEmail + '\'' +
                ", phone='" + phone + '\'' +
                ", acknowledgement='" + acknowledgement + '\'' +
                '}';
    }
}
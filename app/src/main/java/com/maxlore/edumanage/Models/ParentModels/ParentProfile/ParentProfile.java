package com.maxlore.edumanage.Models.ParentModels.ParentProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 9/1/2016.
 */
public class ParentProfile {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("admission_no")
    @Expose
    private String admissionNo;
    @SerializedName("admission_date")
    @Expose
    private String admissionDate;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birth_place")
    @Expose
    private String birthPlace;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("school_email")
    @Expose
    private String schoolEmail;
    @SerializedName("class_teacher")
    @Expose
    private String classTeacher;
    @SerializedName("student_id")
    @Expose
    private int studentid;
    @SerializedName("acknowledgement")
    @Expose
    private String acknowledgement;
    @SerializedName("father_name")
    @Expose
    private String fatherName;
    @SerializedName("mother_name")
    @Expose
    private String motherName;

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
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
     * The admissionNo
     */
    public String getAdmissionNo() {
        return admissionNo;
    }

    /**
     *
     * @param admissionNo
     * The admission_no
     */
    public void setAdmissionNo(String admissionNo) {
        this.admissionNo = admissionNo;
    }

    /**
     *
     * @return
     * The admissionDate
     */
    public String getAdmissionDate() {
        return admissionDate;
    }

    /**
     *
     * @param admissionDate
     * The admission_date
     */
    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    /**
     *
     * @return
     * The dob
     */
    public String getDob() {
        return dob;
    }

    /**
     *
     * @param dob
     * The dob
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     *
     * @return
     * The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     * The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     * The birthPlace
     */
    public String getBirthPlace() {
        return birthPlace;
    }

    /**
     *
     * @param birthPlace
     * The birth_place
     */
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    /**
     *
     * @return
     * The religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     *
     * @param religion
     * The religion
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
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
     * The classTeacher
     */
    public String getClassTeacher() {
        return classTeacher;
    }

    /**
     *
     * @param classTeacher
     * The class_teacher
     */
    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    public int getStudentid() {
        return studentid;
    }

    public void setStudentid(int studentid) {
        this.studentid = studentid;
    }

    public String getAcknowledgement() {
        return acknowledgement;
    }

    public void setAcknowledgement(String acknowledgement) {
        this.acknowledgement = acknowledgement;
    }

    @Override
    public String toString() {
        return "ParentProfile{" +
                "name='" + name + '\'' +
                ", admissionNo='" + admissionNo + '\'' +
                ", admissionDate='" + admissionDate + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", religion='" + religion + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", schoolEmail='" + schoolEmail + '\'' +
                ", classTeacher='" + classTeacher + '\'' +
                ", studentid='" + studentid + '\'' +
                ", acknowledgement='" + acknowledgement + '\'' +
                '}';
    }
}

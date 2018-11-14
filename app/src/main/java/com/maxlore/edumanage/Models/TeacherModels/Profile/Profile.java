package com.maxlore.edumanage.Models.TeacherModels.Profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikhil on 03-07-2016.
 */
//Changed by Shrey on 07-sep-2017
public class Profile {
    @SerializedName("school_email")
    @Expose
    private String email;
    @SerializedName("primary_email")
    @Expose
    private String primaryemail;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("martial_status")
    @Expose
    private String martialStatus;
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("joining_date")
    @Expose
    private String joiningDate;
    @SerializedName("pan_card")
    @Expose
    private String panCard;
    @SerializedName("aadhaar_card")
    @Expose
    private String aadhaarCard;
    @SerializedName("license")
    @Expose
    private String license;
    @SerializedName("acknowledgement")
    @Expose
    private String acknowledgement;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("emp_code")
    @Expose
    private String empcode;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return The dateOfBirth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth The date_of_birth
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return The martialStatus
     */
    public String getMartialStatus() {
        return martialStatus;
    }

    /**
     * @param martialStatus The martial_status
     */
    public void setMartialStatus(String martialStatus) {
        this.martialStatus = martialStatus;
    }

    /**
     * @return The bloodGroup
     */
    public String getBloodGroup() {
        return bloodGroup;
    }

    /**
     * @param bloodGroup The blood_group
     */
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * @param mobileNo The mobile_no
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * @return The joiningDate
     */
    public String getJoiningDate() {
        return joiningDate;
    }

    /**
     * @param joiningDate The joining_date
     */
    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    /**
     * @return The panCard
     */
    public String getPanCard() {
        return panCard;
    }

    /**
     * @param panCard The pan_card
     */
    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    /**
     * @return The aadhaarCard
     */
    public String getAadhaarCard() {
        return aadhaarCard;
    }

    /**
     * @param aadhaarCard The aadhaar_card
     */
    public void setAadhaarCard(String aadhaarCard) {
        this.aadhaarCard = aadhaarCard;
    }

    /**
     * @return The license
     */
    public String getLicense() {
        return license;
    }

    /**
     * @param license The license
     */
    public void setLicense(String license) {
        this.license = license;
    }

    public String getAcknowledgement() {
        return acknowledgement;
    }

    public void setAcknowledgement(String acknowledgement) {
        this.acknowledgement = acknowledgement;
    }

    public String getPrimaryemail() {
        return primaryemail;
    }

    public void setPrimaryemail(String primaryemail) {
        this.primaryemail = primaryemail;
    }

    public String getEmpcode() {
        return empcode;
    }

    public void setEmpcode(String empcode) {
        this.empcode = empcode;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "email='" + email + '\'' +
                ", primaryemail='" + primaryemail + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", martialStatus='" + martialStatus + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", status='" + status + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", joiningDate='" + joiningDate + '\'' +
                ", panCard='" + panCard + '\'' +
                ", aadhaarCard='" + aadhaarCard + '\'' +
                ", license='" + license + '\'' +
                ", acknowledgement='" + acknowledgement + '\'' +
                ", department='" + department + '\'' +
                ", empcode='" + empcode + '\'' +
                '}';
    }
}

package com.maxlore.edumanage.Models.TeacherModels.StudentInfor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 8/23/2016.
 */
public class StudentInformation {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("add_line1")
    @Expose
    private String addLine1;
    @SerializedName("add_line2")
    @Expose
    private String addLine2;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("ph_number")
    @Expose
    private String phNumber;
    @SerializedName("attendance_type")
    @Expose
    private String attendanceType;
    @SerializedName("ugc_board_name")
    @Expose
    private String ugcBoardName;
    @SerializedName("registration_number")
    @Expose
    private String registrationNumber;
    @SerializedName("tax_type")
    @Expose
    private String taxType;
    @SerializedName("logo_file_name")
    @Expose
    private String logoFileName;
    @SerializedName("payslip_generation_date")
    @Expose
    private String payslipGenerationDate;
    @SerializedName("domain")
    @Expose
    private String domain;

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
     * The addLine1
     */
    public String getAddLine1() {
        return addLine1;
    }

    /**
     *
     * @param addLine1
     * The add_line1
     */
    public void setAddLine1(String addLine1) {
        this.addLine1 = addLine1;
    }

    /**
     *
     * @return
     * The addLine2
     */
    public String getAddLine2() {
        return addLine2;
    }

    /**
     *
     * @param addLine2
     * The add_line2
     */
    public void setAddLine2(String addLine2) {
        this.addLine2 = addLine2;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The state
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The district
     */
    public String getDistrict() {
        return district;
    }

    /**
     *
     * @param district
     * The district
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     *
     * @return
     * The pincode
     */
    public String getPincode() {
        return pincode;
    }

    /**
     *
     * @param pincode
     * The pincode
     */
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    /**
     *
     * @return
     * The phNumber
     */
    public String getPhNumber() {
        return phNumber;
    }

    /**
     *
     * @param phNumber
     * The ph_number
     */
    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    /**
     *
     * @return
     * The attendanceType
     */
    public String getAttendanceType() {
        return attendanceType;
    }

    /**
     *
     * @param attendanceType
     * The attendance_type
     */
    public void setAttendanceType(String attendanceType) {
        this.attendanceType = attendanceType;
    }

    /**
     *
     * @return
     * The ugcBoardName
     */
    public String getUgcBoardName() {
        return ugcBoardName;
    }

    /**
     *
     * @param ugcBoardName
     * The ugc_board_name
     */
    public void setUgcBoardName(String ugcBoardName) {
        this.ugcBoardName = ugcBoardName;
    }

    /**
     *
     * @return
     * The registrationNumber
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     *
     * @param registrationNumber
     * The registration_number
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /**
     *
     * @return
     * The taxType
     */
    public String getTaxType() {
        return taxType;
    }

    /**
     *
     * @param taxType
     * The tax_type
     */
    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    /**
     *
     * @return
     * The logoFileName
     */
    public String getLogoFileName() {
        return logoFileName;
    }

    /**
     *
     * @param logoFileName
     * The logo_file_name
     */
    public void setLogoFileName(String logoFileName) {
        this.logoFileName = logoFileName;
    }

    /**
     *
     * @return
     * The payslipGenerationDate
     */
    public String getPayslipGenerationDate() {
        return payslipGenerationDate;
    }

    /**
     *
     * @param payslipGenerationDate
     * The payslip_generation_date
     */
    public void setPayslipGenerationDate(String payslipGenerationDate) {
        this.payslipGenerationDate = payslipGenerationDate;
    }

    /**
     *
     * @return
     * The domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     *
     * @param domain
     * The domain
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "StudentInformation{" +
                "name='" + name + '\'' +
                ", addLine1='" + addLine1 + '\'' +
                ", addLine2='" + addLine2 + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", pincode='" + pincode + '\'' +
                ", phNumber='" + phNumber + '\'' +
                ", attendanceType='" + attendanceType + '\'' +
                ", ugcBoardName='" + ugcBoardName + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", taxType='" + taxType + '\'' +
                ", logoFileName='" + logoFileName + '\'' +
                ", payslipGenerationDate='" + payslipGenerationDate + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}

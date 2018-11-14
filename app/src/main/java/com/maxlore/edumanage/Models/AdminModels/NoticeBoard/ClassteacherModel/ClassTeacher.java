package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.ClassteacherModel;

/**
 * Created by maxlore on 28-Sep-16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassTeacher {

    @SerializedName("section_id")
    @Expose
    private Integer sectionId;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("standard_id")
    @Expose
    private Integer standardId;
    @SerializedName("standard_name")
    @Expose
    private String standardName;
    @SerializedName("class_teacher_status")
    @Expose
    private Integer classTeacherStatus;
    @SerializedName("class_teacher_id")
    @Expose
    private Integer classTeacherId;
    @SerializedName("class_teacher_name")
    @Expose
    private String classTeacherName;
    @SerializedName("employee_id")
    @Expose
    private Integer employeeId;
    @SerializedName("photo")
    @Expose
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     *
     * @return
     * The sectionId
     */
    public Integer getSectionId() {
        return sectionId;
    }

    /**
     *
     * @param sectionId
     * The section_id
     */
    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    /**
     *
     * @return
     * The sectionName
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     *
     * @param sectionName
     * The section_name
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     *
     * @return
     * The standardId
     */
    public Integer getStandardId() {
        return standardId;
    }

    /**
     *
     * @param standardId
     * The standard_id
     */
    public void setStandardId(Integer standardId) {
        this.standardId = standardId;
    }

    /**
     *
     * @return
     * The standardName
     */
    public String getStandardName() {
        return standardName;
    }

    /**
     *
     * @param standardName
     * The standard_name
     */
    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    /**
     *
     * @return
     * The classTeacherStatus
     */
    public Integer getClassTeacherStatus() {
        return classTeacherStatus;
    }

    /**
     *
     * @param classTeacherStatus
     * The class_teacher_status
     */
    public void setClassTeacherStatus(Integer classTeacherStatus) {
        this.classTeacherStatus = classTeacherStatus;
    }

    /**
     *
     * @return
     * The classTeacherId
     */
    public Integer getClassTeacherId() {
        return classTeacherId;
    }

    /**
     *
     * @param classTeacherId
     * The class_teacher_id
     */
    public void setClassTeacherId(Integer classTeacherId) {
        this.classTeacherId = classTeacherId;
    }

    /**
     *
     * @return
     * The classTeacherName
     */
    public String getClassTeacherName() {
        return classTeacherName;
    }

    /**
     *
     * @param classTeacherName
     * The class_teacher_name
     */
    public void setClassTeacherName(String classTeacherName) {
        this.classTeacherName = classTeacherName;
    }

    /**
     *
     * @return
     * The employeeId
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     *
     * @param employeeId
     * The employee_id
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "ClassTeacher{" +
                "sectionId=" + sectionId +
                ", sectionName='" + sectionName + '\'' +
                ", standardId=" + standardId +
                ", standardName='" + standardName + '\'' +
                ", classTeacherStatus=" + classTeacherStatus +
                ", classTeacherId=" + classTeacherId +
                ", classTeacherName='" + classTeacherName + '\'' +
                ", employeeId=" + employeeId +
                '}';
    }
}
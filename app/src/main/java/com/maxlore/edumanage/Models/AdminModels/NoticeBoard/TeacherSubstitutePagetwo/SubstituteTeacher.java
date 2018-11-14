package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.TeacherSubstitutePagetwo;

/**
 * Created by maxlore on 08-Dec-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubstituteTeacher {

    @SerializedName("employee_id")
    @Expose
    private String employeeId;
    @SerializedName("employee_name")
    @Expose
    private String employeeFirstName;
    @SerializedName("employees_contact_no")
    @Expose
    private String employeeMobileno;
    @SerializedName("teacher_substitution_id")
    @Expose
    private Integer teachersubstitutionid;
    @SerializedName("class_section_id")
    @Expose
    private Integer classsectionid;
    @SerializedName("class_subject_id")
    @Expose
    private Integer classsubjectid;
    @SerializedName("photo")
    @Expose
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    private boolean isSelected;

    /**
     * @return The employeeId
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId The employee_id
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return The employeeFirstName
     */
    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    /**
     * @param employeeFirstName The employee_first_name
     */
    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }
    /**
     * @return The employeeLastName
     */

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getEmployeeMobileno() {
        return employeeMobileno;
    }

    public void setEmployeeMobileno(String employeeMobileno) {
        this.employeeMobileno = employeeMobileno;
    }

    public Integer getTeachersubstitutionid() {
        return teachersubstitutionid;
    }

    public void setTeachersubstitutionid(Integer teachersubstitutionid) {
        this.teachersubstitutionid = teachersubstitutionid;
    }

    public Integer getClasssectionid() {
        return classsectionid;
    }

    public void setClasssectionid(Integer classsectionid) {
        this.classsectionid = classsectionid;
    }

    public Integer getClasssubjectid() {
        return classsubjectid;
    }

    public void setClasssubjectid(Integer classsubjectid) {
        this.classsubjectid = classsubjectid;
    }

    @Override
    public String toString() {
        return "SubstituteTeacher{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeFirstName='" + employeeFirstName + '\'' +
                ", employeeMobileno='" + employeeMobileno + '\'' +
                ", teachersubstitutionid=" + teachersubstitutionid +
                ", classsectionid=" + classsectionid +
                ", classsubjectid=" + classsubjectid +
                ", isSelected=" + isSelected +
                '}';
    }
}
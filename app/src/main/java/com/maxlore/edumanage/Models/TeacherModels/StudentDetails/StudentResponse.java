package com.maxlore.edumanage.Models.TeacherModels.StudentDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxlore on 8/10/2016.
 */
public class StudentResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("class_section")
    @Expose
    private List<StudentDetail> classSection = new ArrayList<StudentDetail>();
    @SerializedName("message")
    @Expose
    private String message;

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The classSection
     */
    public List<StudentDetail> getClassSection() {
        return classSection;
    }

    /**
     *
     * @param classSection
     * The class_section
     */
    public void setClassSection(List<StudentDetail> classSection) {
        this.classSection = classSection;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}

package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 10/4/2016.
 */
public class ClassTimeTable {
    @SerializedName("section_id")
    @Expose
    private Integer sectionId;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("standard_name")
    @Expose
    private String standardName;
    @SerializedName("class_teacher_name")
    @Expose
    private String classTeacherName;
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

    @Override
    public String toString() {
        return "ClassTimeTable{" +
                "sectionId=" + sectionId +
                ", sectionName='" + sectionName + '\'' +
                ", standardName='" + standardName + '\'' +
                ", classTeacherName='" + classTeacherName + '\'' +
                '}';
    }
}

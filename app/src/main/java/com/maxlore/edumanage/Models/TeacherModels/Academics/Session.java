package com.maxlore.edumanage.Models.TeacherModels.Academics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 8/10/2016.
 */
public class Session {

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
    @SerializedName("subject_id")
    @Expose
    private Integer subjectId;
    @SerializedName("subject_name")
    @Expose
    private String subjectName;

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
     * The subjectId
     */
    public Integer getSubjectId() {
        return subjectId;
    }

    /**
     *
     * @param subjectId
     * The subject_id
     */
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    /**
     *
     * @return
     * The subjectName
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     *
     * @param subjectName
     * The subject_name
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "Session{" +
                "sectionId=" + sectionId +
                ", sectionName='" + sectionName + '\'' +
                ", standardId=" + standardId +
                ", standardName='" + standardName + '\'' +
                ", subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}

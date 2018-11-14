package com.maxlore.edumanage.Models.ParentModels.ParentSyllabus;

/**
 * Created by maxlore on 30-Aug-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Syllabu {

    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("syllabus")
    @Expose
    private String syllabus;
    @SerializedName("has_practicle")
    @Expose
    private Boolean hasPracticle;

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

    /**
     *
     * @return
     * The code
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @return
     * The syllabus
     */
    public String getSyllabus() {
        return syllabus;
    }

    /**
     *
     * @param syllabus
     * The syllabus
     */
    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    /**
     *
     * @return
     * The hasPracticle
     */
    public Boolean getHasPracticle() {
        return hasPracticle;
    }

    /**
     *
     * @param hasPracticle
     * The has_practicle
     */
    public void setHasPracticle(Boolean hasPracticle) {
        this.hasPracticle = hasPracticle;
    }

    @Override
    public String toString() {
        return "Syllabu{" +
                "subjectName='" + subjectName + '\'' +
                ", code='" + code + '\'' +
                ", syllabus='" + syllabus + '\'' +
                ", hasPracticle=" + hasPracticle +
                '}';
    }
}
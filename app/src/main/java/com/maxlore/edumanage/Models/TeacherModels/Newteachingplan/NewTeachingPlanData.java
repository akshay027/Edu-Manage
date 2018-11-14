package com.maxlore.edumanage.Models.TeacherModels.Newteachingplan;

/**
 * Created by Shrey on 11-Sep-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewTeachingPlanData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("class_name")
    @Expose
    private String className;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("teacher_name")
    @Expose
    private String teacherName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("subject_id")
    @Expose
    private Integer subjectId;
    @SerializedName("section_id")
    @Expose
    private Integer sectionId;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public String toString() {
        return "NewTeachingPlanData{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", subject='" + subject + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", image='" + image + '\'' +
                ", subjectId=" + subjectId +
                ", sectionId=" + sectionId +
                '}';
    }
}
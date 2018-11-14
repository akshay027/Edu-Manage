package com.maxlore.edumanage.Models.TeacherModels.StudentDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 8/18/2016.
 */
public class StudentDetail {

    @SerializedName("section_id")
    @Expose
    private int sectionid;
    @SerializedName("standard_name")
    @Expose
    private String standardName;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("subject_name")
    @Expose
    private String subjectname;

    public int getSectionid() {
        return sectionid;
    }

    public void setSectionid(int sectionid) {
        this.sectionid = sectionid;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }
}
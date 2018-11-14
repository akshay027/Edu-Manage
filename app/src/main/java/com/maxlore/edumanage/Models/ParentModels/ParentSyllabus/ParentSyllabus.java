package com.maxlore.edumanage.Models.ParentModels.ParentSyllabus;

/**
 * Created by maxlore on 30-Aug-16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParentSyllabus {


    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("file_name")
    @Expose
    private String filename;
    @SerializedName("file_path")
    @Expose
    private String filepath;
    @SerializedName("has_practicle")
    @Expose
    private Boolean hasPracticle;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Boolean getHasPracticle() {
        return hasPracticle;
    }

    public void setHasPracticle(Boolean hasPracticle) {
        this.hasPracticle = hasPracticle;
    }

    @Override
    public String toString() {
        return "ParentSyllabus{" +
                "subjectName='" + subjectName + '\'' +
                ", code='" + code + '\'' +
                ", filename='" + filename + '\'' +
                ", filepath='" + filepath + '\'' +
                ", hasPracticle=" + hasPracticle +
                '}';
    }
}
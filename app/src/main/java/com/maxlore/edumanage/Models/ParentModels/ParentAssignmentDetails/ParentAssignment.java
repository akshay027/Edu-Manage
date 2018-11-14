package com.maxlore.edumanage.Models.ParentModels.ParentAssignmentDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 8/31/2016.
 */
public class ParentAssignment {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("teacher_name")
    @Expose
    private String teacherName;
    @SerializedName("offline")
    @Expose
    private Boolean offline;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("due_date")
    @Expose
    private String dueDate;
    @SerializedName("image")
    @Expose
    private String photo;
    @SerializedName("file_name")
    @Expose
    private String fileName;
    @SerializedName("file_path")
    @Expose
    private String filePath;

    public Boolean getOffline() {
        return offline;
    }

    public void setOffline(Boolean offline) {
        this.offline = offline;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return The title
     */

    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question The question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return The teacherName
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * @param teacherName The teacher_name
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    /**
     * @return The offline
     */


    /**
     * @return The subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject The subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return The flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag The flag
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "ParentAssignment{" +
                "title='" + title + '\'' +
                ", question='" + question + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", offline=" + offline +
                ", subject='" + subject + '\'' +
                ", flag='" + flag + '\'' +
                ", phone='" + phone + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", photo='" + photo + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath=" + filePath +
                '}';
    }
}
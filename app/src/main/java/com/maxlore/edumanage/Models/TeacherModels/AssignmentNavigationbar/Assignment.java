package com.maxlore.edumanage.Models.TeacherModels.AssignmentNavigationbar;

/**
 * Created by maxlore on 10-Aug-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Assignment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("standard")
    @Expose
    private String standard;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("due_date")
    @Expose
    private String dueDate;
    @SerializedName("student_count")
    @Expose
    private Integer studentCount;
    @SerializedName("total_submitted")
    @Expose
    private Integer totalSubmitted;
    @SerializedName("student_left")
    @Expose
    private Integer studentLeft;

    private boolean a, b;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The standard
     */
    public String getStandard() {
        return standard;
    }

    /**
     * @param standard The standard
     */
    public void setStandard(String standard) {
        this.standard = standard;
    }

    /**
     * @return The section
     */
    public String getSection() {
        return section;
    }

    /**
     * @param section The section
     */
    public void setSection(String section) {
        this.section = section;
    }

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
     * @return The dueDate
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate The due_date
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return The studentCount
     */
    public Integer getStudentCount() {
        return studentCount;
    }

    /**
     * @param studentCount The student_count
     */
    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    /**
     * @return The totalSubmitted
     */
    public Integer getTotalSubmitted() {
        return totalSubmitted;
    }

    /**
     * @param totalSubmitted The total_submitted
     */
    public void setTotalSubmitted(Integer totalSubmitted) {
        this.totalSubmitted = totalSubmitted;
    }

    /**
     * @return The studentLeft
     */
    public Integer getStudentLeft() {
        return studentLeft;
    }

    /**
     * @param studentLeft The student_left
     */
    public void setStudentLeft(Integer studentLeft) {
        this.studentLeft = studentLeft;
    }

    public boolean isA() {
        return a;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", standard='" + standard + '\'' +
                ", section='" + section + '\'' +
                ", subject='" + subject + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", studentCount=" + studentCount +
                ", totalSubmitted=" + totalSubmitted +
                ", studentLeft=" + studentLeft +
                '}';
    }
}
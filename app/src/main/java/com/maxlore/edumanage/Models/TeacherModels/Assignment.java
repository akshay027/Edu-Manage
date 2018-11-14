package com.maxlore.edumanage.Models.TeacherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikhil on 05-06-2016.
 */
public class Assignment {

    @SerializedName("standard")
    @Expose
    private String standard;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("question")
    @Expose
    private String question;

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


    @Override
    public String toString() {
        return "Assignment{" +
                "standard='" + standard + '\'' +
                ", section='" + section + '\'' +
                ", subject='" + subject + '\'' +
                ", title='" + title + '\'' +
                ", question='" + question + '\'' +
                '}';
    }
}

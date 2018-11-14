package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.TeacherSubstitutePagetwo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubstituteSubject {

    @SerializedName("timetable_assing_id")
    @Expose
    private String timetableAssingId;
    @SerializedName("structure_id")
    @Expose
    private String structureId;
    @SerializedName("timetable_day_id")
    @Expose
    private String timetableDayId;
    @SerializedName("timetable_defination_id")
    @Expose
    private String timetableDefinationId;
    @SerializedName("section_id")
    @Expose
    private String sectionId;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("standard")
    @Expose
    private String standard;
    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("period_id")
    @Expose
    private String periodId;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("from_time")
    @Expose
    private String fromTime;
    @SerializedName("to_time")
    @Expose
    private String toTime;

    /**
     * @return The timetableAssingId
     */
    public String getTimetableAssingId() {
        return timetableAssingId;
    }

    /**
     * @param timetableAssingId The timetable_assing_id
     */
    public void setTimetableAssingId(String timetableAssingId) {
        this.timetableAssingId = timetableAssingId;
    }

    /**
     * @return The structureId
     */
    public String getStructureId() {
        return structureId;
    }

    /**
     * @param structureId The structure_id
     */
    public void setStructureId(String structureId) {
        this.structureId = structureId;
    }

    /**
     * @return The timetableDayId
     */
    public String getTimetableDayId() {
        return timetableDayId;
    }

    /**
     * @param timetableDayId The timetable_day_id
     */
    public void setTimetableDayId(String timetableDayId) {
        this.timetableDayId = timetableDayId;
    }

    /**
     * @return The timetableDefinationId
     */
    public String getTimetableDefinationId() {
        return timetableDefinationId;
    }

    /**
     * @param timetableDefinationId The timetable_defination_id
     */
    public void setTimetableDefinationId(String timetableDefinationId) {
        this.timetableDefinationId = timetableDefinationId;
    }

    /**
     * @return The sectionId
     */
    public String getSectionId() {
        return sectionId;
    }

    /**
     * @param sectionId The section_id
     */
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
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
     * @return The classId
     */
    public String getClassId() {
        return classId;
    }

    /**
     * @param classId The class_id
     */
    public void setClassId(String classId) {
        this.classId = classId;
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
     * @return The subjectId
     */
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * @param subjectId The subject_id
     */
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
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
     * @return The periodId
     */
    public String getPeriodId() {
        return periodId;
    }

    /**
     * @param periodId The period_id
     */
    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    /**
     * @return The period
     */
    public String getPeriod() {
        return period;
    }

    /**
     * @param period The period
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * @return The fromTime
     */
    public String getFromTime() {
        return fromTime;
    }

    /**
     * @param fromTime The from_time
     */
    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    /**
     * @return The toTime
     */
    public String getToTime() {
        return toTime;
    }

    /**
     * @param toTime The to_time
     */
    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    @Override
    public String toString() {
        return "SubstituteSubject{" +
                "timetableAssingId='" + timetableAssingId + '\'' +
                ", structureId='" + structureId + '\'' +
                ", timetableDayId='" + timetableDayId + '\'' +
                ", timetableDefinationId='" + timetableDefinationId + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", section='" + section + '\'' +
                ", classId='" + classId + '\'' +
                ", standard='" + standard + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", subject='" + subject + '\'' +
                ", periodId='" + periodId + '\'' +
                ", period='" + period + '\'' +
                ", fromTime='" + fromTime + '\'' +
                ", toTime='" + toTime + '\'' +
                '}';
    }
}
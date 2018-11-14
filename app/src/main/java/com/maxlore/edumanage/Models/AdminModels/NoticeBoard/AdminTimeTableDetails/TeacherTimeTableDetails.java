package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTableDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by maxlore on 10/5/2016.
 */
@Table
public class TeacherTimeTableDetails extends SugarRecord {
    @SerializedName("teacher")
    @Expose
    private String teacher;
    @SerializedName("own_class")
    @Expose
    private Integer ownClass;
    @SerializedName("structure_id")
    @Expose
    private Integer structureId;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("section")
    @Expose
    private String section;

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    /**
     *
     * @return
     * The ownClass
     */
    public Integer getOwnClass() {
        return ownClass;
    }

    /**
     *
     * @param ownClass
     * The own_class
     */
    public void setOwnClass(Integer ownClass) {
        this.ownClass = ownClass;
    }

    /**
     *
     * @return
     * The structureId
     */
    public Integer getStructureId() {
        return structureId;
    }

    /**
     *
     * @param structureId
     * The structure_id
     */
    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    /**
     *
     * @return
     * The day
     */
    public String getDay() {
        return day;
    }

    /**
     *
     * @param day
     * The day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     *
     * @return
     * The period
     */
    public String getPeriod() {
        return period;
    }

    /**
     *
     * @param period
     * The period
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     *
     * @return
     * The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime
     * The start_time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @return
     * The endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     *
     * @param endTime
     * The end_time
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     *
     * @return
     * The subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     *
     * @param subject
     * The subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     *
     * @return
     * The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     *
     * @return
     * The _class
     */
    public String getClass_() {
        return _class;
    }

    /**
     *
     * @param _class
     * The class
     */
    public void setClass_(String _class) {
        this._class = _class;
    }

    /**
     *
     * @return
     * The section
     */
    public String getSection() {
        return section;
    }

    /**
     *
     * @param section
     * The section
     */
    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return "TeacherTimeTableDetails{" +
                "ownClass=" + ownClass +
                ", structureId=" + structureId +
                ", day='" + day + '\'' +
                ", period='" + period + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", subject='" + subject + '\'' +
                ", code=" + code +
                ", _class='" + _class + '\'' +
                ", section='" + section + '\'' +
                '}';
    }
}


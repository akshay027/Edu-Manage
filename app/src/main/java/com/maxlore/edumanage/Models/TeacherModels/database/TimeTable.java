package com.maxlore.edumanage.Models.TeacherModels.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by maxlore on 04-Aug-16.
 */
@Table
public class TimeTable extends SugarRecord {

    @SerializedName("structure_id")
    @Expose
    private String structureId;
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
    @SerializedName("teacher")
    @Expose
    private String teacher;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("own_class")
    @Expose
    private Integer ownClass;
    @SerializedName("code")
    @Expose
    private Integer code;

    public String getStructureId() {
        return structureId;
    }

    public void setStructureId(String structureId) {
        this.structureId = structureId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public Integer getOwnClass() {
        return ownClass;
    }

    public void setOwnClass(Integer ownClass) {
        this.ownClass = ownClass;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "structureId='" + structureId + '\'' +
                ", day='" + day + '\'' +
                ", period='" + period + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", subject='" + subject + '\'' +
                ", teacher='" + teacher + '\'' +
                ", section='" + section + '\'' +
                ", _class='" + _class + '\'' +
                ", ownClass=" + ownClass +
                ", code=" + code +
                '}';
    }
}
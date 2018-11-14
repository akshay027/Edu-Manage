package com.maxlore.edumanage.Models.TeacherModels.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Table;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxlore on 04-Aug-16.
 */
@Table
public class TimeTableStructure extends SugarRecord {

    @SerializedName("timetable_struct_id")
    @Expose
    private int structureId;
    @SerializedName("name")
    @Expose
    private String name;
    @Ignore
    @SerializedName("time_table")
    @Expose
    private List<TimeTable> timeTable = new ArrayList<TimeTable>();

    public TimeTableStructure() {
    }
    public int getStructureId() {
        return structureId;
    }

    public void setStructureId(int id) {
        this.structureId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TimeTable> getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(List<TimeTable> timeTable) {
        this.timeTable = timeTable;
    }

    @Override
    public String toString() {
        return "TimeTableStructure{" +
                "structureId=" + structureId +
                ", name='" + name + '\'' +
                ", timeTable=" + timeTable +
                '}';
    }
}

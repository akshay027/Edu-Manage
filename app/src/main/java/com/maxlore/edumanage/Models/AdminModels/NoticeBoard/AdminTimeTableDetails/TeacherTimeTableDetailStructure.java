package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTableDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxlore on 10/5/2016.
 */
public class TeacherTimeTableDetailStructure  {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("time_table")
    @Expose
    private List<TeacherTimeTableDetails> timeTable = new ArrayList<TeacherTimeTableDetails>();

    public TeacherTimeTableDetailStructure() {
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The timeTable
     */
    public List<TeacherTimeTableDetails> getTimeTable() {
        return timeTable;
    }

    /**
     *
     * @param timeTable
     * The time_table
     */
    public void setTimeTable(List<TeacherTimeTableDetails> timeTable) {
        this.timeTable = timeTable;
    }

    @Override
    public String toString() {
        return "TeacherTimeTableDetailStructure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timeTable=" + timeTable +
                '}';
    }


}


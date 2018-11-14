package com.maxlore.edumanage.Models.TeacherModels.Leaves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nikhil on 18-06-2016.
 */
public class LeaveCategory implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("max_leave")
    @Expose
    private String maxLeave;
    @SerializedName("available_leave")
    @Expose
    private String availableLeave;
    @SerializedName("leave_type")
    @Expose
    private Boolean leavetypeper;


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
     * @return The maxLeave
     */
    public String getMaxLeave() {
        return maxLeave;
    }

    /**
     * @param maxLeave The max_leave
     */
    public void setMaxLeave(String maxLeave) {
        this.maxLeave = maxLeave;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAvailableLeave() {
        return availableLeave;
    }

    public void setAvailableLeave(String availableLeave) {
        this.availableLeave = availableLeave;
    }

    public Boolean getLeavetypeper() {
        return leavetypeper;
    }

    public void setLeavetypeper(Boolean leavetypeper) {
        this.leavetypeper = leavetypeper;
    }

    @Override
    public String toString() {
        return "LeaveCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", maxLeave='" + maxLeave + '\'' +
                ", availableLeave='" + availableLeave + '\'' +
                ", leavetypeper=" + leavetypeper +
                '}';
    }
}


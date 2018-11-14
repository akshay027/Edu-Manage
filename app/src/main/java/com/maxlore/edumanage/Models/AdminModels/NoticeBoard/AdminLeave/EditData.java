package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminLeave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by maxlore on 11/9/2016.
 */

public class EditData implements Serializable {

    @SerializedName("leave_type_id")
    @Expose
    private Integer leaveTypeId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("permission")
    @Expose
    private Boolean permission;



    /**
     *
     * @return
     * The leaveTypeId
     */
    public Integer getLeaveTypeId() {
        return leaveTypeId;
    }

    /**
     *
     * @param leaveTypeId
     * The leave_type_id
     */
    public void setLeaveTypeId(Integer leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
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

    public Boolean getPermission() {
        return permission;
    }

    public void setPermission(Boolean permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "EditData{" +
                "leaveTypeId=" + leaveTypeId +
                ", name='" + name + '\'' +
                ", permission=" + permission +
                '}';
    }
}


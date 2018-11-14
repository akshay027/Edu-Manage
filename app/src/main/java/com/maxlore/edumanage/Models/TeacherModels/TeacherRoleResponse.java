package com.maxlore.edumanage.Models.TeacherModels;

/**
 * Created by Shrey on 30-Aug-17.
 */

import java.util.List;

import com.maxlore.edumanage.Models.TeacherRole;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherRoleResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("roles")
    @Expose
    private List<TeacherRole> roles = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<TeacherRole> getRoles() {
        return roles;
    }

    public void setRoles(List<TeacherRole> roles) {
        this.roles = roles;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TeacherRoleResponse{" +
                "status=" + status +
                ", roles=" + roles +
                ", message='" + message + '\'' +
                '}';
    }
}
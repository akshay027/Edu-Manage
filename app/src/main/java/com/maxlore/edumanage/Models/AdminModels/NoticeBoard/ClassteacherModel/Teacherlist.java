package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.ClassteacherModel;

/**
 * Created by maxlore on 28-Sep-16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Teacherlist {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("list_teacher")
    @Expose
    private List<ListTeacher> listTeacher = new ArrayList<ListTeacher>();
    @SerializedName("message")
    @Expose
    private String message;

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The listTeacher
     */
    public List<ListTeacher> getListTeacher() {
        return listTeacher;
    }

    /**
     *
     * @param listTeacher
     * The list_teacher
     */
    public void setListTeacher(List<ListTeacher> listTeacher) {
        this.listTeacher = listTeacher;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Teacherlist{" +
                "status=" + status +
                ", listTeacher=" + listTeacher +
                ", message='" + message + '\'' +
                '}';
    }
}
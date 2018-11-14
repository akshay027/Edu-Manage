package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminTimeTableDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxlore on 10/5/2016.
 */
public class TeacherTimeTableDetailResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("list")
    @Expose
    private List<TeacherTimeTableDetailStructure> list = new ArrayList<TeacherTimeTableDetailStructure>();
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
     * The list
     */
    public List<TeacherTimeTableDetailStructure> getList() {
        return list;
    }

    /**
     *
     * @param list
     * The list
     */
    public void setList(List<TeacherTimeTableDetailStructure> list) {
        this.list = list;
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
        return "TeacherTimeTableDetailResponse{" +
                "status=" + status +
                ", list=" + list +
                ", message='" + message + '\'' +
                '}';
    }
}



package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.TeacherSubstitutePagetwo;

/**
 * Created by maxlore on 08-Dec-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubstituteTeacherResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("subtitute_teacher_list")
    @Expose
    private List<SubstituteTeacher> subtituteTeacherList ;
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
     * The subtituteTeacherList
     */
    public List<SubstituteTeacher> getSubtituteTeacherList() {
        return subtituteTeacherList;
    }

    /**
     *
     * @param subtituteTeacherList
     * The subtitute_teacher_list
     */
    public void setSubtituteTeacherList(List<SubstituteTeacher> subtituteTeacherList) {
        this.subtituteTeacherList = subtituteTeacherList;
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
        return "SubstituteTeacherResponse{" +
                "status=" + status +
                ", subtituteTeacherList=" + subtituteTeacherList +
                ", message='" + message + '\'' +
                '}';
    }
}
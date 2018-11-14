package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.ClassteacherModel;

/**
 * Created by maxlore on 28-Sep-16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ClassTeacherResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("class_teacher")
    @Expose
    private List<ClassTeacher> classTeacher = new ArrayList<ClassTeacher>();
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
     * The classTeacher
     */
    public List<ClassTeacher> getClassTeacher() {
        return classTeacher;
    }

    /**
     *
     * @param classTeacher
     * The class_teacher
     */
    public void setClassTeacher(List<ClassTeacher> classTeacher) {
        this.classTeacher = classTeacher;
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
        return "ClassTeacherResponse{" +
                "status=" + status +
                ", classTeacher=" + classTeacher +
                ", message='" + message + '\'' +
                '}';
    }
}


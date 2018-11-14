package com.maxlore.edumanage.Models.TeacherModels.Leaves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikhil on 18-06-2016.
 */
public class LeaveResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("leave")
    @Expose
    private Leave leave;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * @return The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return The leave
     */
    public Leave getLeave() {
        return leave;
    }

    /**
     * @param leave The leave
     */
    public void setLeave(Leave leave) {
        this.leave = leave;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LeaveResponse{" +
                "status=" + status +
                ", leave=" + leave +
                ", message='" + message + '\'' +
                '}';
    }
}

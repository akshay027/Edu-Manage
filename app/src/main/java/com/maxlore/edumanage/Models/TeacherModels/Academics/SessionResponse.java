package com.maxlore.edumanage.Models.TeacherModels.Academics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxlore on 8/10/2016.
 */
public class SessionResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("session")
    @Expose
    private List<Session> session = new ArrayList<Session>();
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
     * The session
     */
    public List<Session> getSession() {
        return session;
    }

    /**
     *
     * @param session
     * The session
     */
    public void setSession(List<Session> session) {
        this.session = session;
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
        return "SessionResponse{" +
                "status=" + status +
                ", session=" + session +
                ", message='" + message + '\'' +
                '}';
    }
}


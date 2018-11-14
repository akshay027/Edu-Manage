package com.maxlore.edumanage.Models.AdminModels.NoticeBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxlore on 9/23/2016.
 */
public class NoticeBoardResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("notice")
    @Expose
    private List<Notice> notice = new ArrayList<Notice>();
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
     * The notice
     */
    public List<Notice> getNotice() {
        return notice;
    }

    /**
     *
     * @param notice
     * The notice
     */
    public void setNotice(List<Notice> notice) {
        this.notice = notice;
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
        return "NoticeBoardResponse{" +
                "status=" + status +
                ", notice=" + notice +
                ", message='" + message + '\'' +
                '}';
    }
}

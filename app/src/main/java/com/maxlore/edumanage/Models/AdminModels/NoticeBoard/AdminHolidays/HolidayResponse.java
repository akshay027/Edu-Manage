package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminHolidays;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxlore on 11/25/2016.
 */

public class HolidayResponse {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("holiday")
    @Expose
    private List<Holiday> holiday = new ArrayList<Holiday>();
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
     * The holiday
     */
    public List<Holiday> getHoliday() {
        return holiday;
    }

    /**
     *
     * @param holiday
     * The holiday
     */
    public void setHoliday(List<Holiday> holiday) {
        this.holiday = holiday;
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
        return "HolidayResponse{" +
                "status=" + status +
                ", holiday=" + holiday +
                ", message='" + message + '\'' +
                '}';
    }
}

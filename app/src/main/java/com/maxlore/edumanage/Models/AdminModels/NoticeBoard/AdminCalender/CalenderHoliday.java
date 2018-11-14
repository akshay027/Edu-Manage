package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminCalender;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 3/30/2017.
 */

public class CalenderHoliday {
    @SerializedName("holiday_type")
    @Expose
    private String holidayType;
    @SerializedName("reason")
    @Expose
    private String reason;

    public String getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(String holidayType) {
        this.holidayType = holidayType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminHolidays;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 11/25/2016.
 */

public class Holiday {

    @SerializedName("holiday_id")
    @Expose
    private String holidayId;
    @SerializedName("holiday_date")
    @Expose
    private String holidayDate;
    @SerializedName("holiday_type")
    @Expose
    private String holidayType;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("holiday_end_date")
    @Expose
    private String holidayEndDate;

    public String getHolidayId() {
        return holidayId;
    }

    public void setHolidayId(String holidayId) {
        this.holidayId = holidayId;
    }

    /**
     *
     * @return
     * The holidayDate
     */
    public String getHolidayDate() {
        return holidayDate;
    }

    /**
     *
     * @param holidayDate
     * The holiday_date
     */
    public void setHolidayDate(String holidayDate) {
        this.holidayDate = holidayDate;
    }

    /**
     *
     * @return
     * The holidayType
     */
    public String getHolidayType() {
        return holidayType;
    }

    /**
     *
     * @param holidayType
     * The holiday_type
     */
    public void setHolidayType(String holidayType) {
        this.holidayType = holidayType;
    }

    /**
     *
     * @return
     * The reason
     */
    public String getReason() {
        return reason;
    }

    /**
     *
     * @param reason
     * The reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     *
     * @return
     * The holidayEndDate
     */
    public String getHolidayEndDate() {
        return holidayEndDate;
    }

    /**
     *
     * @param holidayEndDate
     * The holiday_end_date
     */
    public void setHolidayEndDate(String holidayEndDate) {
        this.holidayEndDate = holidayEndDate;
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "holidayDate='" + holidayDate + '\'' +
                ", holidayType='" + holidayType + '\'' +
                ", reason='" + reason + '\'' +
                ", holidayEndDate='" + holidayEndDate + '\'' +
                '}';
    }
}

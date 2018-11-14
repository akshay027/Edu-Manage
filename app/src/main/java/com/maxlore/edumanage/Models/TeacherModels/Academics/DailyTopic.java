package com.maxlore.edumanage.Models.TeacherModels.Academics;

/**
 * Created by maxlore on 23-Oct-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyTopic {

    @SerializedName("daily_plan_id")
    @Expose
    private Integer dailyPlanId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("lesson_name")
    @Expose
    private String lessonName;


    /**
     * @return The dailyPlanId
     */
    public Integer getDailyPlanId() {
        return dailyPlanId;
    }

    /**
     * @param dailyPlanId The daily_plan_id
     */
    public void setDailyPlanId(Integer dailyPlanId) {
        this.dailyPlanId = dailyPlanId;
    }

    /**
     * @return The date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return The lessonName
     */
    public String getLessonName() {
        return lessonName;
    }

    /**
     * @param lessonName The lesson_name
     */
    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }


    @Override
    public String toString() {
        return "DailyTopic{" +
                "dailyPlanId=" + dailyPlanId +
                ", date='" + date + '\'' +
                ", lessonName='" + lessonName + '\'' +

                '}';
    }
}

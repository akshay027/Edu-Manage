package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminCalender;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 3/28/2017.
 */

public class FeeType {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("grace_period")
    @Expose
    private String gracePeriod;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(String gracePeriod) {
        this.gracePeriod = gracePeriod;
    }


}

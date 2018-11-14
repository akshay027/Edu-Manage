package com.maxlore.edumanage.Models.AdminModels.NoticeBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maxlore on 09-Mar-17.
 */

public class AdminAbsentStudent {

    @SerializedName("absent")
    @Expose
    private List<AdminAbsent> absent;
    @SerializedName("leave")
    @Expose
    private List<AdminAbsentLeave> AdminAbsentLeave;

    public List<AdminAbsent> getAbsent() {
        return absent;
    }

    public void setAbsent(List<AdminAbsent> absent) {
        this.absent = absent;
    }

    public List<AdminAbsentLeave> getAdminAbsentLeave() {
        return AdminAbsentLeave;
    }

    public void setAdminAbsentLeave(List<AdminAbsentLeave> AdminAbsentLeave) {
        this.AdminAbsentLeave = AdminAbsentLeave;
    }
}

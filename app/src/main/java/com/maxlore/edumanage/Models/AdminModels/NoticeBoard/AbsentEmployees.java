package com.maxlore.edumanage.Models.AdminModels.NoticeBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maxlore on 10-Mar-17.
 */

public class AbsentEmployees {

    @SerializedName("absent")
    @Expose
    private List<AdminTeacherAbsent> absent = null;
    @SerializedName("leave")
    @Expose
    private List<AdminTeacherLeave> leave = null;

    public List<AdminTeacherAbsent> getAbsent() {
        return absent;
    }

    public void setAbsent(List<AdminTeacherAbsent> absent) {
        this.absent = absent;
    }

    public List<AdminTeacherLeave> getLeave() {
        return leave;
    }

    public void setLeave(List<AdminTeacherLeave> leave) {
        this.leave = leave;
    }


}

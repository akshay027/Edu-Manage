package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAssignSubject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 9/29/2016.
 */
public class AssignSubject {
    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("flag")
    @Expose
    private Integer flag;

    /**
     *
     * @return
     * The subjectName
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     *
     * @param subjectName
     * The subject_name
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     *
     * @return
     * The flag
     */
    public Integer getFlag() {
        return flag;
    }

    /**
     *
     * @param flag
     * The flag
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }


    @Override
    public String toString() {
        return "AssignSubject{" +
                "subjectName='" + subjectName + '\'' +
                ", flag=" + flag +
                '}';
    }
}

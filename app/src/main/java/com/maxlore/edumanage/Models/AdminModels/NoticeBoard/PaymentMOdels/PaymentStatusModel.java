package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels;

/**
 * Created by Shrey on 26-Jul-17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentStatusModel {
    @Override
    public String toString() {
        return "PaymentStatusModel{" +
                "name='" + name + '\'' +
                ", checked=" + checked +
                ", check_box=" + check_box +
                '}';
    }

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("checked")
    @Expose
    private Boolean checked;
    @SerializedName("checkall")
    @Expose
    private  Integer check_box = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getCheck_box() {
        return check_box;
    }

    public void setCheck_box(Integer check_box) {
        this.check_box = check_box;
    }
}
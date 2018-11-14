package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminBranchesResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shrey on 13-Sep-17.
 */

public class BranchesData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BranchesData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

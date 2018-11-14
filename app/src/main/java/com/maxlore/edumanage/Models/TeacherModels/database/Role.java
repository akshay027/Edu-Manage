package com.maxlore.edumanage.Models.TeacherModels.database;

/**
 * Created by maxlore on 26-Nov-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table
public class Role extends SugarRecord {

    @SerializedName("role_name")
    @Expose
    private String roleName;
    @SerializedName("redirect_to")
    @Expose
    private String redirectTo;

    public Role() {
    }

    public Role(String roleName, String redirectTo) {

        this.roleName = roleName;
        this.redirectTo = redirectTo;
    }

    /**
     *
     * @return
     * The roleName
     */

    public String getRoleName() {
        return roleName;
    }

    /**
     *
     * @param roleName
     * The role_name
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     *
     * @return
     * The redirectTo
     */
    public String getRedirectTo() {
        return redirectTo;
    }

    /**
     *
     * @param redirectTo
     * The redirect_to
     */
    public void setRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleName='" + roleName + '\'' +
                ", redirectTo='" + redirectTo + '\'' +
                '}';
    }
}
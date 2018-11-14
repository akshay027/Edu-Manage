package com.maxlore.edumanage.Models.TeacherModels.AssignmentDetail;

/**
 * Created by maxlore on 11-Aug-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("parent_name")
    @Expose
    private String parentName;
    @SerializedName("contact_no")
    @Expose
    private String contactNo;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("photo")
    @Expose
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The parentName
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * @param parentName The parent_name
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * @return The contactNo
     */
    public String getContactNo() {
        return contactNo;
    }

    /**
     * @param contactNo The contact_no
     */
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentName='" + parentName + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", type='" + type + '\'' +
                ", status=" + status +
                ", photo='" + photo + '\'' +
                '}';
    }
}
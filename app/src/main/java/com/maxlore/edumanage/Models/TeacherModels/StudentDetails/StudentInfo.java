package com.maxlore.edumanage.Models.TeacherModels.StudentDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 8/20/2016.
 */
public class StudentInfo {


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

    @Override
    public String toString() {
        return "StudentInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentName='" + parentName + '\'' +
                ", contactNo='" + contactNo + '\'' +
                '}';
    }
}

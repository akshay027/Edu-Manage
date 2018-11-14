package com.maxlore.edumanage.Models.TeacherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikhil on 05-06-2016.
 */
public class HouseGroup {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("student_count")
    @Expose
    private Integer studentCount;

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
     * @return The points
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * @param points The points
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     * @return The studentCount
     */
    public Integer getStudentCount() {
        return studentCount;
    }

    /**
     * @param studentCount The student_count
     */
    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    @Override
    public String toString() {
        return "HouseGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", points=" + points +
                ", studentCount=" + studentCount +
                '}';
    }
}

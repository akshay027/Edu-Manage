package com.maxlore.edumanage.Models.TeacherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikhil on 05-06-2016.
 */
public class Dashboard {

    @SerializedName("attendence_count")
    @Expose
    private Integer attendenceCount;
    @SerializedName("class_today")
    @Expose
    private Integer classToday;
    @SerializedName("leave_balance")
    @Expose
    private Integer leaveBalance;

    @SerializedName("quotes")
    @Expose
    private List<Quote> quotes = new ArrayList<Quote>();
    @SerializedName("announcement")
    @Expose
    private List<Announcement> announcement = new ArrayList<Announcement>();
    @SerializedName("house_group")
    @Expose
    private List<HouseGroup> houseGroup = new ArrayList<HouseGroup>();
    @SerializedName("library_corner")
    @Expose
    private List<LibraryCorner> libraryCorner = new ArrayList<LibraryCorner>();
    @SerializedName("assignments")
    @Expose
    private List<Assignment> assignments = new ArrayList<Assignment>();

    @SerializedName("timetables")
    @Expose
    private List<DashboardTimeTable> timetables = new ArrayList<DashboardTimeTable>();
    @SerializedName("image")
    @Expose
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return The attendenceCount
     */

    public Integer getAttendenceCount() {
        return attendenceCount;
    }

    /**
     * @param attendenceCount The attendence_count
     */
    public void setAttendenceCount(Integer attendenceCount) {
        this.attendenceCount = attendenceCount;
    }

    /**
     * @return The classToday
     */
    public Integer getClassToday() {
        return classToday;
    }

    /**
     * @param classToday The class_today
     */
    public void setClassToday(Integer classToday) {
        this.classToday = classToday;
    }

    /**
     * @return The leaveBalance
     */
    public Integer getLeaveBalance() {
        return leaveBalance;
    }

    /**
     * @param leaveBalance The leave_balance
     */
    public void setLeaveBalance(Integer leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

    /**
     * @return The mailCount
     */


    /**
     * @param mailCount The mail_count
     */



    /**
     * @return The announcement
     */
    public List<Announcement> getAnnouncement() {
        return announcement;
    }

    /**
     * @param announcement The announcement
     */
    public void setAnnouncement(List<Announcement> announcement) {
        this.announcement = announcement;
    }

    /**
     * @return The quotes
     */
    public List<Quote> getQuotes() {
        return quotes;
    }

    /**
     * @param quotes The quotes
     */
    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }

    /**
     * @return The houseGroup
     */
    public List<HouseGroup> getHouseGroup() {
        return houseGroup;
    }

    /**
     * @param houseGroup The house_group
     */
    public void setHouseGroup(List<HouseGroup> houseGroup) {
        this.houseGroup = houseGroup;
    }

    /**
     * @return The libraryCorner
     */
    public List<LibraryCorner> getLibraryCorner() {
        return libraryCorner;
    }

    /**
     * @param libraryCorner The library_corner
     */
    public void setLibraryCorner(List<LibraryCorner> libraryCorner) {
        this.libraryCorner = libraryCorner;
    }

    /**
     * @return The assignments
     */
    public List<Assignment> getAssignments() {
        return assignments;
    }

    /**
     * @param assignments The assignments
     */
    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    /**
     * @return The events
     */

    /**
     * @param events The events
     */


    /**
     * @return The timetables
     */
    public List<DashboardTimeTable> getTimetables() {
        return timetables;
    }

    /**
     * @param timetables The timetables
     */
    public void setTimetables(List<DashboardTimeTable> timetables) {
        this.timetables = timetables;
    }


    @Override
    public String toString() {
        return "Dashboard{" +
                "attendenceCount=" + attendenceCount +
                ", classToday=" + classToday +
                ", leaveBalance=" + leaveBalance +
                ", quotes=" + quotes +
                ", announcement=" + announcement +
                ", houseGroup=" + houseGroup +
                ", libraryCorner=" + libraryCorner +
                ", assignments=" + assignments +
                ", timetables=" + timetables +
                ", image='" + image + '\'' +
                '}';
    }
}


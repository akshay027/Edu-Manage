package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminDashBoard;


/**
 * Created by maxlore on 23-Sep-16.
 */


import com.maxlore.edumanage.Models.TeacherModels.Announcement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminDashboard {
    @SerializedName("announcement")
    @Expose
    private List<Announcement> announcement = null;
    @SerializedName("student_count")
    @Expose
    private Integer studentCount;
    @SerializedName("teaching_count")
    @Expose
    private Integer teachingCount;
    @SerializedName("non_teaching_count")
    @Expose
    private Integer nonTeachingCount;
    @SerializedName("absent_employees_count")
    @Expose
    private Integer absentEmployeesCount;
    @SerializedName("absent_students_count")
    @Expose
    private Integer absentStudentsCount;
    @SerializedName("transfer_certificate_count")
    @Expose
    private Integer transferCertificateCount;
    @SerializedName("resignation_count")
    @Expose
    private Integer resignationCount;
    @SerializedName("teaching_attendance_percentage")
    @Expose
    private Integer teachingAttendancePercentage;
    @SerializedName("non_teaching_attendance_percentage")
    @Expose
    private Integer nonTeachingAttendancePercentage;
    @SerializedName("student_attendance_percentage")
    @Expose
    private Integer studentAttendancePercentage;
    @SerializedName("image")
    @Expose
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Announcement> getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(List<Announcement> announcement) {
        this.announcement = announcement;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public Integer getTeachingCount() {
        return teachingCount;
    }

    public void setTeachingCount(Integer teachingCount) {
        this.teachingCount = teachingCount;
    }

    public Integer getNonTeachingCount() {
        return nonTeachingCount;
    }

    public void setNonTeachingCount(Integer nonTeachingCount) {
        this.nonTeachingCount = nonTeachingCount;
    }

    public Integer getAbsentEmployeesCount() {
        return absentEmployeesCount;
    }

    public void setAbsentEmployeesCount(Integer absentEmployeesCount) {
        this.absentEmployeesCount = absentEmployeesCount;
    }

    public Integer getAbsentStudentsCount() {
        return absentStudentsCount;
    }

    public void setAbsentStudentsCount(Integer absentStudentsCount) {
        this.absentStudentsCount = absentStudentsCount;
    }

    public Integer getTransferCertificateCount() {
        return transferCertificateCount;
    }

    public void setTransferCertificateCount(Integer transferCertificateCount) {
        this.transferCertificateCount = transferCertificateCount;
    }

    public Integer getResignationCount() {
        return resignationCount;
    }

    public void setResignationCount(Integer resignationCount) {
        this.resignationCount = resignationCount;
    }

    public Integer getTeachingAttendancePercentage() {
        return teachingAttendancePercentage;
    }

    public void setTeachingAttendancePercentage(Integer teachingAttendancePercentage) {
        this.teachingAttendancePercentage = teachingAttendancePercentage;
    }

    public Integer getNonTeachingAttendancePercentage() {
        return nonTeachingAttendancePercentage;
    }

    public void setNonTeachingAttendancePercentage(Integer nonTeachingAttendancePercentage) {
        this.nonTeachingAttendancePercentage = nonTeachingAttendancePercentage;
    }

    public Integer getStudentAttendancePercentage() {
        return studentAttendancePercentage;
    }

    public void setStudentAttendancePercentage(Integer studentAttendancePercentage) {
        this.studentAttendancePercentage = studentAttendancePercentage;
    }

    @Override
    public String toString() {
        return "AdminDashboard{" +
                "announcement=" + announcement +
                ", studentCount=" + studentCount +
                ", teachingCount=" + teachingCount +
                ", nonTeachingCount=" + nonTeachingCount +
                ", absentEmployeesCount=" + absentEmployeesCount +
                ", absentStudentsCount=" + absentStudentsCount +
                ", transferCertificateCount=" + transferCertificateCount +
                ", resignationCount=" + resignationCount +
                ", teachingAttendancePercentage=" + teachingAttendancePercentage +
                ", nonTeachingAttendancePercentage=" + nonTeachingAttendancePercentage +
                ", studentAttendancePercentage=" + studentAttendancePercentage +
                '}';
    }
}
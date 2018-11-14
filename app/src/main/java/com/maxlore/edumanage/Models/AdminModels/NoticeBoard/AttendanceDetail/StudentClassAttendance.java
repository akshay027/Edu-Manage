package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AttendanceDetail;

/**
 * Created by maxlore on 06-Oct-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentClassAttendance {

    @SerializedName("student_id")
    @Expose
    private Integer studentId;
    @SerializedName("student_name")
    @Expose
    private String studentName;
    @SerializedName("attendance_id")
    @Expose
    private Integer attendanceId;
    @SerializedName("attendance")
    @Expose
    private Integer attendance;
    @SerializedName("attendance_pm")
    @Expose
    private Integer attendancePm;
    @SerializedName("checkall")
    @Expose
    private Integer check_box = 0;
    private boolean isPMSelected = false, isMark;
    @SerializedName("photo")
    @Expose
    private String photo;

    /**
     * @return The studentId
     */
    public Integer getStudentId() {
        return studentId;
    }

    /**
     * @param studentId The student_id
     */
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    /**
     * @return The studentName
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * @param studentName The student_name
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

        /**
         *
         * @return
         * The attendanceId
         */
        public Integer getAttendanceId() {
            return attendanceId;
        }

    /**
     * @param attendanceId The attendance_id
     */
    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    /**
     * @return The attendance
     */
    public Integer getAttendance() {
        return attendance;
    }

    /**
     * @param attendance The attendance
     */
    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    /**
     * @return The attendancePm
     */
    public Integer getAttendancePm() {
        return attendancePm;
    }

    /**
     * @param attendancePm The attendance_pm
     */
    public void setAttendancePm(Integer attendancePm) {
        this.attendancePm = attendancePm;
    }

    public boolean isPMSelected() {
        return isPMSelected;
    }

    public void setPMSelected(boolean PMSelected) {
        isPMSelected = PMSelected;
    }

    public boolean isMark() {
        return isMark;
    }

    public void setMark(boolean mark) {
        isMark = mark;
    }

    public Integer getCheck_box() {
        return check_box;
    }

    public void setCheck_box(Integer check_box) {
        this.check_box = check_box;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "StudentClassAttendance{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", attendanceId=" + attendanceId +
                ", attendance=" + attendance +
                ", attendancePm=" + attendancePm +
                ", check_box=" + check_box +
                ", isPMSelected=" + isPMSelected +
                ", isMark=" + isMark +
                ", photo='" + photo + '\'' +
                '}';
    }
}
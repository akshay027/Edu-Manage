package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AttendanceDetail;

/**
 * Created by maxlore on 06-Oct-16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DepartmentAttendance {

    @SerializedName("employee_id")
    @Expose
    private Integer employeeId;
    @SerializedName("employee_name")
    @Expose
    private String employeeName;
    @SerializedName("attendance_id")
    @Expose
    private Integer attendanceId;
    @SerializedName("attendance")
    @Expose
    private Integer attendance;
    @SerializedName("checkall")
    @Expose
    private  Integer check_box = 0;
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
     *
     * @return
     * The employeeId
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     *
     * @param employeeId
     * The employee_id
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**
     *
     * @return
     * The employeeName
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     *
     * @param employeeName
     * The employee_name
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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
     *
     * @param attendanceId
     * The attendance_id
     */
    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    /**
     *
     * @return
     * The attendance
     */
    public Integer getAttendance() {
        return attendance;
    }

    /**
     *
     * @param attendance
     * The attendance
     */
    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public Integer getCheck_box() {
        return check_box;
    }

    public void setCheck_box(Integer check_box) {
        this.check_box = check_box;
    }

    @Override
    public String toString() {
        return "DepartmentAttendance{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", attendanceId=" + attendanceId +
                ", attendance=" + attendance +
                ", check_box=" + check_box +
                '}';
    }
}

package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AttendanceDetail;

/**
 * Created by maxlore on 06-Oct-16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class StudentsResponse{

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("attendance_type")
        @Expose
        private Boolean attendanceType;
        @SerializedName("list")
        @Expose
        private java.util.List<StudentClassAttendance> list = new ArrayList<StudentClassAttendance>();
        @SerializedName("message")
        @Expose
        private String message;

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

        /**
         * @return The attendanceType
         */
        public Boolean getAttendanceType() {
            return attendanceType;
        }

        /**
         * @param attendanceType The attendance_type
         */
        public void setAttendanceType(Boolean attendanceType) {
            this.attendanceType = attendanceType;
        }

        /**
         * @return The list
         */
        public java.util.List<StudentClassAttendance> getList() {
            return list;
        }

        /**
         * @param list The list
         */
        public void setList(java.util.List<StudentClassAttendance> list) {
            this.list = list;
        }

        /**
         * @return The message
         */
        public String getMessage() {
            return message;
        }

        /**
         * @param message The message
         */
        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "StudentAttendanceResponse{" +
                    "status=" + status +
                    ", attendanceType=" + attendanceType +
                    ", list=" + list +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminAttendance;

/**
 * Created by maxlore on 04-Oct-16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

    public class StudentAttendanceResponse {


        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("list")
        @Expose
        private java.util.List<AdminStudentAttendance> list = new ArrayList<AdminStudentAttendance>();
        @SerializedName("message")
        @Expose
        private String message;

        /**
         *
         * @return
         * The status
         */
        public Integer getStatus() {
            return status;
        }

        /**
         *
         * @param status
         * The status
         */
        public void setStatus(Integer status) {
            this.status = status;
        }

        /**
         *
         * @return
         * The list
         */
        public java.util.List<AdminStudentAttendance> getList() {
            return list;
        }

        /**
         *
         * @param list
         * The list
         */
        public void setList(java.util.List<AdminStudentAttendance> list) {
            this.list = list;
        }

        /**
         *
         * @return
         * The message
         */
        public String getMessage() {
            return message;
        }

        /**
         *
         * @param message
         * The message
         */
        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "StudentAttendanceResponse{" +
                    "status=" + status +
                    ", list=" + list +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
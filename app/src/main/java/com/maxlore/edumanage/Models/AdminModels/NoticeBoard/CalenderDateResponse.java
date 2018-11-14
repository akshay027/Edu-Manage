package com.maxlore.edumanage.Models.AdminModels.NoticeBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by maxlore on 3/31/2017.
 */

public class CalenderDateResponse {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("dates")
        @Expose
        private ArrayList<String> events;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public ArrayList<String> getEvents() {
            return events;
        }

        public void setEvents(ArrayList<String> events) {
            this.events = events;
        }

}

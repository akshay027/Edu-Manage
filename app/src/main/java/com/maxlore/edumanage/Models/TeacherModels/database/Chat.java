package com.maxlore.edumanage.Models.TeacherModels.database;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by Nikhil on 9/13/2016.
 */
@Table
public class Chat extends SugarRecord {


    private int messageId;
    private int type;
    private String message;
    private String date;
    private String time;
    private int status;
    private int userId;
//    private String chatId;

//    public String getChatId() {
//        return chatId;
//    }
//
//    public void setChatId(String chatId) {
//        this.chatId = chatId;
//    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "messageId=" + messageId +
                ", type=" + type +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", status=" + status +
                ", userId=" + userId +
//                ", chatId='" + chatId + '\'' +
                '}';
    }
}
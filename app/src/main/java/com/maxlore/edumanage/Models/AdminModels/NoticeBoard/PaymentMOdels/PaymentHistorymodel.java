package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels;

/**
 * Created by Shrey on 21-Jul-17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentHistorymodel {

    @SerializedName("student_name")
    @Expose
    private String studentName;
    @SerializedName("student_class")
    @Expose
    private String studentClass;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("payment_for")
    @Expose
    private String paymentFor;
    @SerializedName("reciept_no")
    @Expose
    private String recieptNo;
    @SerializedName("reciept_date")
    @Expose
    private String recieptDate;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("status")
    @Expose
    private String status;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPaymentFor() {
        return paymentFor;
    }

    public void setPaymentFor(String paymentFor) {
        this.paymentFor = paymentFor;
    }

    public String getRecieptNo() {
        return recieptNo;
    }

    public void setRecieptNo(String recieptNo) {
        this.recieptNo = recieptNo;
    }

    public String getRecieptDate() {
        return recieptDate;
    }

    public void setRecieptDate(String recieptDate) {
        this.recieptDate = recieptDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PaymentHistorymodel{" +
                "studentName='" + studentName + '\'' +
                ", studentClass='" + studentClass + '\'' +
                ", photo='" + photo + '\'' +
                ", paymentFor='" + paymentFor + '\'' +
                ", recieptNo='" + recieptNo + '\'' +
                ", recieptDate='" + recieptDate + '\'' +
                ", amount='" + amount + '\'' +
                ", mode='" + mode + '\'' +
                ", remarks='" + remarks + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
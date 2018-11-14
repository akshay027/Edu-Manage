package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.PaymentMOdels;

/**
 * Created by Shrey on 25-Jul-17.
 */


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FiltersModel {


    @SerializedName("payment_for")
    @Expose
    private List<PaymentForModel> paymentFor = null;
    @SerializedName("payment_status")
    @Expose
    private List<PaymentStatusModel> paymentStatus = null;

    public List<PaymentForModel> getPaymentFor() {
        return paymentFor;
    }

    public void setPaymentFor(List<PaymentForModel> paymentFor) {
        this.paymentFor = paymentFor;
    }

    public List<PaymentStatusModel> getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(List<PaymentStatusModel> paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "FiltersModel{" +
                "paymentFor=" + paymentFor +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}
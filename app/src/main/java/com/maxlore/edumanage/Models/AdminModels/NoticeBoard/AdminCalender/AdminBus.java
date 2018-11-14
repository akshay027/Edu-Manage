package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.AdminCalender;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 3/28/2017.
 */

public class AdminBus {

    @SerializedName("bus_no")
    @Expose
    private String busNo;
    @SerializedName("permit")
    @Expose
    private String permitUpto;
    @SerializedName("tax")
    @Expose
    private String taxPaidUpto;
    @SerializedName("break_fitness")
    @Expose
    private String breakFitnessUpto;
    @SerializedName("insurance")
    @Expose
    private String insuranceUpto;
    @SerializedName("pollution")
    @Expose
    private String pollutionUpto;

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getPermitUpto() {
        return permitUpto;
    }

    public void setPermitUpto(String permitUpto) {
        this.permitUpto = permitUpto;
    }

    public String getTaxPaidUpto() {
        return taxPaidUpto;
    }

    public void setTaxPaidUpto(String taxPaidUpto) {
        this.taxPaidUpto = taxPaidUpto;
    }

    public String getBreakFitnessUpto() {
        return breakFitnessUpto;
    }

    public void setBreakFitnessUpto(String breakFitnessUpto) {
        this.breakFitnessUpto = breakFitnessUpto;
    }

    public String getInsuranceUpto() {
        return insuranceUpto;
    }

    public void setInsuranceUpto(String insuranceUpto) {
        this.insuranceUpto = insuranceUpto;
    }

    public String getPollutionUpto() {
        return pollutionUpto;
    }

    public void setPollutionUpto(String pollutionUpto) {
        this.pollutionUpto = pollutionUpto;
    }

}

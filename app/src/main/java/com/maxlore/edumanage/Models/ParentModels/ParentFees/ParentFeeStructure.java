package com.maxlore.edumanage.Models.ParentModels.ParentFees;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maxlore on 9/8/2016.
 */
public class ParentFeeStructure {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("fee_assign")
    @Expose
    private List<Parentfees> feeAssign = null;
    @SerializedName("total_fee")
    @Expose
    private String totalFee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Parentfees> getFeeAssign() {
        return feeAssign;
    }

    public void setFeeAssign(List<Parentfees> feeAssign) {
        this.feeAssign = feeAssign;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    @Override
    public String toString() {
        return "ParentFeeStructure{" +
                "id=" + id +
                ", feeAssign=" + feeAssign +
                ", totalFee='" + totalFee + '\'' +
                '}';
    }
}
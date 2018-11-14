package com.maxlore.edumanage.Models.ParentModels.ParentFees;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by maxlore on 9/8/2016.
 */
public class Parentfees {

    @SerializedName("fee_type")
    @Expose
    private String feeType;
    @SerializedName("fee_type_amount")
    @Expose
    private String feeTypeAmount;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("component_amount")
    @Expose
    private String componentAmount;
    private boolean a, b;

    /**
     * @return The feeType
     */
    public String getFeeType() {
        return feeType;
    }

    /**
     * @param feeType The fee_type
     */
    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    /**
     * @return The feeTypeAmount
     */
    public String getFeeTypeAmount() {
        return feeTypeAmount;
    }

    /**
     * @param feeTypeAmount The fee_type_amount
     */
    public void setFeeTypeAmount(String feeTypeAmount) {
        this.feeTypeAmount = feeTypeAmount;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The componentAmount
     */
    public String getComponentAmount() {
        return componentAmount;
    }

    /**
     * @param componentAmount The component_amount
     */
    public void setComponentAmount(String componentAmount) {
        this.componentAmount = componentAmount;
    }

    public boolean isA() {
        return a;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "Parentfees{" +
                "feeType='" + feeType + '\'' +
                ", feeTypeAmount='" + feeTypeAmount + '\'' +
                ", name='" + name + '\'' +
                ", componentAmount='" + componentAmount + '\'' +
                ", a=" + a +
                ", b=" + b +
                '}';
    }
}
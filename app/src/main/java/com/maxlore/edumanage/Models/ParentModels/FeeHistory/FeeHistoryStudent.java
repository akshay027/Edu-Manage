package com.maxlore.edumanage.Models.ParentModels.FeeHistory;

/**
 * Created by maxlore on 04-Nov-16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FeeHistoryStudent {

    @SerializedName("student_id")
    @Expose
    private Integer studentId;
    @SerializedName("student_name")
    @Expose
    private String studentName;
    @SerializedName("fee_type")
    @Expose
    private List<FeeHistory> feeType = new ArrayList<FeeHistory>();
    @SerializedName("total_fee")
    @Expose
    private Integer totalFee;
    @SerializedName("fee_deposited")
    @Expose
    private Integer feeDeposited;
    @SerializedName("fee_due")
    @Expose
    private Integer feeDue;

    /**
     *
     * @return
     * The studentId
     */
    public Integer getStudentId() {
        return studentId;
    }

    /**
     *
     * @param studentId
     * The student_id
     */
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    /**
     *
     * @return
     * The studentName
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     *
     * @param studentName
     * The student_name
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     *
     * @return
     * The feeType
     */
    public List<FeeHistory> getFeeType() {
        return feeType;
    }

    /**
     *
     * @param feeType
     * The fee_type
     */
    public void setFeeType(List<FeeHistory> feeType) {
        this.feeType = feeType;
    }

    /**
     *
     * @return
     * The totalFee
     */
    public Integer getTotalFee() {
        return totalFee;
    }

    /**
     *
     * @param totalFee
     * The total_fee
     */
    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    /**
     *
     * @return
     * The feeDeposited
     */
    public Integer getFeeDeposited() {
        return feeDeposited;
    }

    /**
     *
     * @param feeDeposited
     * The fee_deposited
     */
    public void setFeeDeposited(Integer feeDeposited) {
        this.feeDeposited = feeDeposited;
    }

    /**
     *
     * @return
     * The feeDue
     */
    public Integer getFeeDue() {
        return feeDue;
    }

    /**
     *
     * @param feeDue
     * The fee_due
     */
    public void setFeeDue(Integer feeDue) {
        this.feeDue = feeDue;
    }

    @Override
    public String toString() {
        return "FeeHistoryStudent{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", feeType=" + feeType +
                ", totalFee=" + totalFee +
                ", feeDeposited=" + feeDeposited +
                ", feeDue=" + feeDue +
                '}';
    }
}
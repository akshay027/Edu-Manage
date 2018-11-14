package com.maxlore.edumanage.Models.ParentModels.FeeHistory;

/**
 * Created by maxlore on 04-Nov-16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeeHistory {

    @SerializedName("fee_type_id")
    @Expose
    private Integer feeTypeId;
    @SerializedName("fee_type_name")
    @Expose
    private String feeTypeName;

    /**
     *
     * @return
     * The feeTypeId
     */
    public Integer getFeeTypeId() {
        return feeTypeId;
    }

    /**
     *
     * @param feeTypeId
     * The fee_type_id
     */
    public void setFeeTypeId(Integer feeTypeId) {
        this.feeTypeId = feeTypeId;
    }

    /**
     *
     * @return
     * The feeTypeName
     */
    public String getFeeTypeName() {
        return feeTypeName;
    }

    /**
     *
     * @param feeTypeName
     * The fee_type_name
     */
    public void setFeeTypeName(String feeTypeName) {
        this.feeTypeName = feeTypeName;
    }

    @Override
    public String toString() {
        return "FeeHistory{" +
                "feeTypeId=" + feeTypeId +
                ", feeTypeName='" + feeTypeName + '\'' +
                '}';
    }
}
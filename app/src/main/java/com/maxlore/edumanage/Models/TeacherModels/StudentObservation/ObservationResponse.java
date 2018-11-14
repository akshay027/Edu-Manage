package com.maxlore.edumanage.Models.TeacherModels.StudentObservation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxlore on 8/22/2016.
 */
public class ObservationResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("observation_info")
    @Expose
    private List<Observation> observationInfo = new ArrayList<Observation>();
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
     * The observationInfo
     */
    public List<Observation> getObservationInfo() {
        return observationInfo;
    }

    /**
     *
     * @param observationInfo
     * The observation_info
     */
    public void setObservationInfo(List<Observation> observationInfo) {
        this.observationInfo = observationInfo;
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
}

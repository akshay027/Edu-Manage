package com.maxlore.edumanage.Models.ParentModels.ParentAssignmentDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by maxlore on 8/31/2016.
 */
public class ParentAssignmentStructure {

    @SerializedName("assignment")
    @Expose
    private List<ParentAssignment> assignment;

    public List<ParentAssignment> getAssignment() {
        return assignment;
    }

    public void setAssignment(List<ParentAssignment> assignment) {
        this.assignment = assignment;
    }

    @Override
    public String toString() {
        return "ParentAssignmentStructure{" +
                "assignment=" + assignment +
                '}';
    }
}
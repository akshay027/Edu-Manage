package com.maxlore.edumanage.Models.TeacherModels.AssignmentNavigation;

/**
 * Created by maxlore on 09-Aug-16.
 */
public class Assignment {
    private String name;
    private String address;

    public Assignment(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
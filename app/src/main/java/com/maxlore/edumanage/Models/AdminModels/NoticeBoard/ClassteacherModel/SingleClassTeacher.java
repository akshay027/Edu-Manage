package com.maxlore.edumanage.Models.AdminModels.NoticeBoard.ClassteacherModel;

/**
 * Created by maxlore on 23-Oct-16.
 */

public class SingleClassTeacher {
    private String name;
    private boolean isSelected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "SingleClassTeacher{" +
                "name='" + name + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}

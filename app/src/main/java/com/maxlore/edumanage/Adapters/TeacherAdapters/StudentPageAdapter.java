package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.maxlore.edumanage.Fragment.ClassStudentFragment;

import com.maxlore.edumanage.Fragment.AllStudentFragment;

/**
 * Created by maxlore on 8/11/2016.
 */
public class StudentPageAdapter extends FragmentPagerAdapter {
    private final int tabCount;

    public StudentPageAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllStudentFragment();
            case 1:
                return new ClassStudentFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return this.tabCount;
    }
}



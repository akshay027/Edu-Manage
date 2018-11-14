package com.maxlore.edumanage.Adapters.TeacherAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.maxlore.edumanage.Fragment.ClassTimeTableFragment;
import com.maxlore.edumanage.Fragment.OwnTimeTableFragment;


/**
 * Created by maxlore on 04-Aug-16.
 */
public class TimeTablePageAdapter extends FragmentPagerAdapter {
    private final int tabCount;
    private String branch_id;

    public TimeTablePageAdapter(FragmentManager fm, int tabCount, String branch_id) {
        super(fm);
        this.tabCount = tabCount;
        this.branch_id = branch_id;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new OwnTimeTableFragment(branch_id);
            case 1:
                return new ClassTimeTableFragment(branch_id);
        }
        return null;
    }

    @Override
    public int getCount() {
        return this.tabCount;
    }
}



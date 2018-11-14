package com.maxlore.edumanage.Adapters.AdminAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.maxlore.edumanage.Fragment.AdminClassTimeTableFragment;
import com.maxlore.edumanage.Fragment.AdminTeacherTimeTableFragment;

/**
 * Created by maxlore on 10/4/2016.
 */
public class AdminTimeTablePageAdapter extends FragmentPagerAdapter {
    private final int tabCount;

    public AdminTimeTablePageAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AdminClassTimeTableFragment();
            case 1:
                return new AdminTeacherTimeTableFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return this.tabCount;
    }
}



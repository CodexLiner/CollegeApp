package com.codingergo.myproject.StudentList.TabbedManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdpaterForTab extends FragmentPagerAdapter {
    int count;
    public AdpaterForTab(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        count = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return  new FirstYear();
            case 1 : return  new SecondYear();
            case 2 : return  new FinalYear();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return count;
    }
}

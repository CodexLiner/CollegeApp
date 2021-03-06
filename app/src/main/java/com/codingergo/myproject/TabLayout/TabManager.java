package com.codingergo.myproject.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.codingergo.myproject.Extras.CeFragment;
import com.codingergo.myproject.Extras.CsFragment;
import com.codingergo.myproject.Extras.EtFragment;
import com.codingergo.myproject.Extras.MeFragment;

public class TabManager extends FragmentPagerAdapter
{
    public TabManager(@NonNull FragmentManager fm, int behavior, int tabno) {
        super(fm, behavior);
        this.tabno = tabno;
    }

    private int tabno;
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0 : return  new CeFragment();
            case 1 :return new CsFragment();
            case  2 : return  new EtFragment();
            case 3 : return  new MeFragment();
            default: return null;

        }

    }

    @Override
    public int getCount() {
        return tabno;
    }
}

package org.techtowm.ex1_study;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    public FragmentPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                return BlankFragmentTab1.newInstance();
            case 1:
                return BlankFragmentTab2.newInstance();
            case 2:
                return BlankFragmentTab3.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

package org.rubychina.app.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import org.rubychina.app.ui.fragment.topic.TopicViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 14-2-2.
 */
public class TopicFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;

    public TopicFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public TopicFragmentPagerAdapter(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
//        if (object instanceof TopicViewFragment) {
//            ((TopicViewFragment) object).update(xyzData);
//        }
        //don't return POSITION_NONE, avoid fragment recreation.
        return POSITION_NONE;
    }

}

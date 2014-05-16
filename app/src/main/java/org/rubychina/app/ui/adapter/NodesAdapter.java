package org.rubychina.app.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.rubychina.app.model.Node;
import org.rubychina.app.ui.fragment.NodeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 14-2-6.
 */
public class NodesAdapter extends FragmentPagerAdapter {

    List<Fragment> mFragments = new ArrayList<Fragment>();
    List<List<Node>> nodes;

    public NodesAdapter(FragmentManager fm) {
        super(fm);
    }

    public NodesAdapter(FragmentManager fm, List<List<Node>> nodes){
        super(fm);
        this.nodes = nodes;
        for(List<Node> li : nodes){
            mFragments.add(new NodeFragment(li));
        }
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
    public CharSequence getPageTitle (int position) {
        return nodes.get(position).get(0).section_name;
    }
}

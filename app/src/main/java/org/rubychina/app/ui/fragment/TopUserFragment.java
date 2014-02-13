package org.rubychina.app.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etsy.android.grid.StaggeredGridView;

import org.rubychina.app.R;
import org.rubychina.app.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 14-2-13.
 */
public class TopUserFragment extends Fragment {
    private StaggeredGridView staggeredGridView;

    private List<User> list = new ArrayList<User>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.fragment_node, null);

        staggeredGridView = (StaggeredGridView) contentView.findViewById(R.id.grid_view);

//        staggeredGridView.setAdapter();

        return contentView;
    }
}

package org.rubychina.app.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etsy.android.grid.StaggeredGridView;

import org.rubychina.app.R;
import org.rubychina.app.model.Node;
import org.rubychina.app.ui.adapter.NodeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 14-2-6.
 */
@SuppressLint("ValidFragment")
public class NodeFragment extends Fragment {
    List<Node> list = new ArrayList<Node>();

    private StaggeredGridView staggeredGridView;

    public NodeFragment(List<Node> li) {
        this.list = li;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.fragment_node, null);

        staggeredGridView = (StaggeredGridView) contentView.findViewById(R.id.grid_view);

        staggeredGridView.setAdapter(new NodeAdapter(getActivity(), list));

        return contentView;
    }
}

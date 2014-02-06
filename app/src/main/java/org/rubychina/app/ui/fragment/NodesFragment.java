package org.rubychina.app.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.rubychina.app.R;
import org.rubychina.app.model.Node;
import org.rubychina.app.ui.adapter.NodesAdapter;
import org.rubychina.app.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 14-2-6.
 */
public class NodesFragment extends Fragment {
    private ViewPager pager;
    private NodesAdapter adapter;
    private List<List<Node>> lists = new ArrayList<List<Node>>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.fragment_nodes, null);

        pager = (ViewPager) contentView.findViewById(R.id.pager);

        fetchData();

        return contentView;
    }

    private void fetchData(){
        ApiUtils.get(ApiUtils.NODES, null, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String responce){
                Gson gson = new Gson();
                List<Node> list = new Gson().fromJson(responce, new TypeToken<ArrayList<Node>>(){}.getType());
                List<String> section_ids = new ArrayList<String>();
                for (Node node : list){
                    if (!section_ids.contains(node.section_id)){
                        section_ids.add(node.section_id);
                    }
                }

                for (String id : section_ids){
                    List<Node> ls = new ArrayList<Node>();
                    for (Node n : list){
                        if (id.equals(n.section_id)) ls.add(n);
                    }
                    lists.add(ls);
                }

                adapter = new NodesAdapter(getChildFragmentManager(), lists);

                pager.setAdapter(adapter);
            }
        });
    }
}

package org.rubychina.app.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etsy.android.grid.StaggeredGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.rubychina.app.R;
import org.rubychina.app.model.Author;
import org.rubychina.app.model.User;
import org.rubychina.app.ui.adapter.NodeAdapter;
import org.rubychina.app.ui.adapter.TopUsersAdapter;
import org.rubychina.app.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 14-2-13.
 */
public class TopUserFragment extends Fragment {
    private StaggeredGridView staggeredGridView;

    private List<User> list = new ArrayList<User>();

    private TopUsersAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.fragment_node, null);

        staggeredGridView = (StaggeredGridView) contentView.findViewById(R.id.grid_view);

        adapter = new TopUsersAdapter(getActivity(), list);

        staggeredGridView.setAdapter(adapter);

        fetchData();

        return contentView;
    }

    private void fetchData(){
        ApiUtils.get(ApiUtils.TOP_USERS, null, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String responce){
                list = new Gson().fromJson(responce, new TypeToken<ArrayList<User>>(){}.getType());
                list.add(Author.getUser1());
                list.add(Author.getUser2());
                adapter = new TopUsersAdapter(getActivity(), list);
                staggeredGridView.setAdapter(adapter);
            }
        });
    }
}

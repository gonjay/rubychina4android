package org.rubychina.app.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.rubychina.app.R;
import org.rubychina.app.model.Topic;
import org.rubychina.app.ui.adapter.FavoriteAdapter;
import org.rubychina.app.ui.adapter.ItemAnimationAdapter;
import org.rubychina.app.utils.ApiParams;
import org.rubychina.app.utils.ApiUtils;
import org.rubychina.app.utils.UserUtils;
import org.rubychina.app.view.LoadingFooter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

/**
 * Created by mac on 14-2-10.
 */
public class FavoriteFragment extends Fragment implements PullToRefreshAttacher.OnRefreshListener {
    private PullToRefreshAttacher mPullToRefreshAttacher;

    private ListView mListView;

    private LoadingFooter mLoadingFooter;

    private FavoriteAdapter mAdapter;

    private String type, url;

    int page = 1;

    Gson gson = new Gson();
    Type listType = new TypeToken<List<Topic>>(){}.getType();
    List<Topic> topics = new ArrayList<Topic>();

    public FavoriteFragment(String type, String url, PullToRefreshAttacher mPullToRefreshAttacher) {
        this.type = type;
        this.url = url;
        this.mPullToRefreshAttacher = mPullToRefreshAttacher;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hot, container, false);
        mListView = (ListView)rootView.findViewById(R.id.listView);

        mPullToRefreshAttacher.setRefreshableView(mListView, this);
        mLoadingFooter = new LoadingFooter(getActivity());
        mListView.addFooterView(mLoadingFooter.getView());

        mAdapter = new FavoriteAdapter(topics, getActivity());
        AnimationAdapter animationAdapter = new ItemAnimationAdapter(mAdapter);
        animationAdapter.setListView(mListView);
        mListView.setAdapter(animationAdapter);

        loadData(1);
        return rootView;
    }

    private void loadData(final int page) {
        ApiUtils.get(url, new ApiParams().with("page", page + "").with("per_page", "15"), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                mLoadingFooter.setState(LoadingFooter.State.Idle, 3000);
                List<Topic> ts = gson.fromJson(response, listType);
                if (page == 1) {
                    topics.clear();
                    mPullToRefreshAttacher.setRefreshComplete();
                    UserUtils.cacheTopic(response, type);
                }
                for (Topic t : ts) {
                    topics.add(t);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onRefreshStarted(View view) {
        loadData(1);
    }
}

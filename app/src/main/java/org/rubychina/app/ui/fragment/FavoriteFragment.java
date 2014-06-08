package org.rubychina.app.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

/**
 * Created by mac on 14-2-10.
 */
public class FavoriteFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_TYPE_KEY = "type";
    private static final String ARG_URL_KEY = "url";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    private LoadingFooter mLoadingFooter;
    private FavoriteAdapter mAdapter;

    private String type, url;

    private int page = 1;

    private final Gson gson = new Gson();
    private Type listType = new TypeToken<List<Topic>>(){}.getType();
    private List<Topic> topics = new ArrayList<Topic>();

    public FavoriteFragment() {
    }

    public static FavoriteFragment newInstance(String type, String url) {
        FavoriteFragment fragment = new FavoriteFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TYPE_KEY, type);
        args.putString(ARG_URL_KEY, url);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hot, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefresh);
        mSwipeRefreshLayout.setColorScheme(R.color.swipe_color_1,
                R.color.swipe_color_2, R.color.swipe_color_3, R.color.swipe_color_4);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mListView = (ListView)rootView.findViewById(R.id.listView);

        mLoadingFooter = new LoadingFooter(getActivity());
        mListView.addFooterView(mLoadingFooter.getView());

        mAdapter = new FavoriteAdapter(topics, getActivity());
        AnimationAdapter animationAdapter = new ItemAnimationAdapter(mAdapter);
        animationAdapter.setAbsListView(mListView);
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
                    mSwipeRefreshLayout.setRefreshing(false);
                    UserUtils.cacheTopic(response, type);
                }
                for (Topic t : ts) {
                    topics.add(t);
                }
                mAdapter.notifyDataSetChanged();
                mLoadingFooter.setState(LoadingFooter.State.TheEnd);
            }
        });
    }

    @Override
    public void onRefresh() {
        loadData(1);
    }
}

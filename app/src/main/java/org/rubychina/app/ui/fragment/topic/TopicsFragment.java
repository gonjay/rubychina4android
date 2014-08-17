package org.rubychina.app.ui.fragment.topic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import org.rubychina.app.R;
import org.rubychina.app.model.Topic;
import org.rubychina.app.ui.adapter.ItemAnimationAdapter;
import org.rubychina.app.ui.adapter.TopicAdapter;
import org.rubychina.app.utils.ApiParams;
import org.rubychina.app.utils.ApiUtils;
import org.rubychina.app.utils.UserUtils;
import org.rubychina.app.view.LoadingFooter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;

/**
 * Created by mac on 14-1-28.
 */
public class TopicsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_TYPE_KEY = "type";
    private static final String ARG_URL_KEY = "url";

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ListView mListView;

    private LoadingFooter mLoadingFooter;

    private TopicAdapter mAdapter;

    private String type, url;

    private boolean hasmore = true;

    private int page = 1;

    private final Gson gson = new Gson();
    private Type listType = new TypeToken<List<Topic>>(){}.getType();
    private List<Topic> topics = new ArrayList<Topic>();

    public TopicsFragment(){}

    public static TopicsFragment newInstance(String type, String url) {
        TopicsFragment fragment = new TopicsFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TYPE_KEY, type);
        args.putString(ARG_URL_KEY, url);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        type = args.getString(ARG_TYPE_KEY);
        url = args.getString(ARG_URL_KEY);

        View rootView = inflater.inflate(R.layout.fragment_hot, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefresh);
        mSwipeRefreshLayout.setColorScheme(R.color.swipe_color_1,
                R.color.swipe_color_2, R.color.swipe_color_3, R.color.swipe_color_4);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mListView = (ListView)rootView.findViewById(R.id.listView);

        mLoadingFooter = new LoadingFooter(getActivity());
        mListView.addFooterView(mLoadingFooter.getView());

        mAdapter = new TopicAdapter(topics, getActivity());

        AnimationAdapter animationAdapter = new ItemAnimationAdapter(mAdapter);
        animationAdapter.setAbsListView(mListView);
        animationAdapter.getViewAnimator().setAnimationDelayMillis(30);
        animationAdapter.getViewAnimator().setAnimationDurationMillis(getResources().getInteger(android.R.integer.config_mediumAnimTime));

        mListView.setAdapter(animationAdapter);
        bindListView();

        loadCacheData();

        return rootView;
    }

    private void bindListView(){
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                if (mLoadingFooter.getState() == LoadingFooter.State.Loading
                        || mLoadingFooter.getState() == LoadingFooter.State.TheEnd) {
                    return;
                }
                if (firstVisibleItem + visibleItemCount >= totalItemCount
                        && totalItemCount != 0
                        && totalItemCount != mListView.getHeaderViewsCount()
                        + mListView.getFooterViewsCount() && mAdapter.getCount() > 0
                        && hasmore) {
                    loadNextPage();
                }
            }
        });
    }

    private void loadNextPage(){
        mLoadingFooter.setState(LoadingFooter.State.Loading);
        page++;
        loadData(page);
    }

    private void loadFirstPage(){
        page = 1;
        loadData(page);
    }

    private void loadCacheData(){
        Log.v("","loadCacheData : " + page);
        mSwipeRefreshLayout.setRefreshing(true);
        String cache = UserUtils.loadTopic(type);
        if (cache.length() > 0){
            List<Topic> ts = gson.fromJson(UserUtils.loadTopic(type), listType);
            for (Topic t : ts){
                topics.add(t);
            }
            mSwipeRefreshLayout.setRefreshing(false);
            mAdapter.notifyDataSetChanged();
            loadFirstPage();
        } else {
            loadFirstPage();
        }
    }

    private void loadData(final int page) {
        ApiUtils.get(url, new ApiParams().with("page", page + "").with("per_page","15"), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                mLoadingFooter.setState(LoadingFooter.State.Idle, 3000);
                List<Topic> ts = gson.fromJson(response, listType);
                if (page == 1){
                    topics.clear();
                    UserUtils.cacheTopic(response, type);
                }
                for (Topic t : ts){
                    topics.add(t);
                }
                mAdapter.notifyDataSetChanged();
                if (ts.size() < 1){
                    hasmore = false;
                    mLoadingFooter.setState(LoadingFooter.State.TheEnd);
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable throwable){
                if(isAdded()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), R.string.load_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        loadFirstPage();
    }
}

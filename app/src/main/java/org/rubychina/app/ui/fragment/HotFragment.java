package org.rubychina.app.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.rubychina.app.R;
import org.rubychina.app.model.Topic;
import org.rubychina.app.ui.MainActivity;
import org.rubychina.app.ui.adapter.ItemAnimationAdapter;
import org.rubychina.app.ui.adapter.TopicAdapter;
import org.rubychina.app.utils.ApiParams;
import org.rubychina.app.utils.ApiUtils;
import org.rubychina.app.utils.TimeFomater;
import org.rubychina.app.utils.UserUtils;
import org.rubychina.app.view.LoadingFooter;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

/**
 * Created by mac on 14-1-28.
 */
public class HotFragment extends Fragment implements PullToRefreshAttacher.OnRefreshListener{

    private PullToRefreshAttacher mPullToRefreshAttacher;

    private ListView mListView;

    private LoadingFooter mLoadingFooter;

    private TopicAdapter mAdapter;

    int page = 1;

    Gson gson = new Gson();
    Type listType = new TypeToken<List<Topic>>(){}.getType();
    List<Topic> topics = new ArrayList<Topic>();

    public HotFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hot, container, false);
        mListView = (ListView)rootView.findViewById(R.id.listView);

        mPullToRefreshAttacher = ((MainActivity)getActivity()).getPullToRefreshAttacher();

        mPullToRefreshAttacher.setRefreshableView(mListView, this);
        mLoadingFooter = new LoadingFooter(getActivity());
        mListView.addFooterView(mLoadingFooter.getView());

        mAdapter = new TopicAdapter(topics);
        AnimationAdapter animationAdapter = new ItemAnimationAdapter(mAdapter);
        animationAdapter.setListView(mListView);
        mListView.setAdapter(animationAdapter);
        bindListView();

//        loadData(page);
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
                        + mListView.getFooterViewsCount() && mAdapter.getCount() > 0) {
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
        loadData(1);
    }

    private void loadCacheData(){
        String cache = UserUtils.loadTopic();
        if (cache.length() > 0){
            List<Topic> ts = gson.fromJson(UserUtils.loadTopic(), listType);
            for (Topic t : ts){
                topics.add(t);
            }
            mAdapter.notifyDataSetChanged();
        } else {
            loadFirstPage();
        }
    }

    private void loadData(final int page) {
        ApiUtils.get(ApiUtils.TOPICS, new ApiParams().with("page", page + "").with("per_page","10"), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                mLoadingFooter.setState(LoadingFooter.State.Idle, 3000);
                List<Topic> ts = gson.fromJson(response, listType);
                if (page == 1){
                    topics.clear();
                    mPullToRefreshAttacher.setRefreshComplete();
                    UserUtils.cacheTopic(response);
                }
                for (Topic t : ts){
                    topics.add(t);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private String getData(){
        return "[{\"id\":17035,\"title\":\"1UL << 12\",\"created_at\":\"2014-01-28T11:14:47.701+08:00\",\"updated_at\":\"2014-01-28T11:29:34.393+08:00\",\"replied_at\":\"2014-01-28T11:29:34+08:00\",\"replies_count\":2,\"node_name\":\"Linux\",\"node_id\":17,\"last_reply_user_id\":5191,\"last_reply_user_login\":\"zhangyuxiu\",\"user\":{\"id\":5191,\"login\":\"zhangyuxiu\",\"avatar_url\":\"http://l.ruby-china.org/user/large_avatar/5191.jpg\"}},{\"id\":17033,\"title\":\"malloc 分配的物理内存在哪个区？\",\"created_at\":\"2014-01-28T09:25:45.758+08:00\",\"updated_at\":\"2014-01-28T11:17:32.206+08:00\",\"replied_at\":\"2014-01-28T11:17:32+08:00\",\"replies_count\":5,\"node_name\":\"Linux\",\"node_id\":17,\"last_reply_user_id\":5191,\"last_reply_user_login\":\"zhangyuxiu\",\"user\":{\"id\":5191,\"login\":\"zhangyuxiu\",\"avatar_url\":\"http://l.ruby-china.org/user/large_avatar/5191.jpg\"}},{\"id\":17024,\"title\":\"WARN -- : Overwriting existing field \",\"created_at\":\"2014-01-27T15:03:04.346+08:00\",\"updated_at\":\"2014-01-28T11:12:49.160+08:00\",\"replied_at\":\"2014-01-28T11:12:49+08:00\",\"replies_count\":5,\"node_name\":\"Rails\",\"node_id\":2,\"last_reply_user_id\":8830,\"last_reply_user_login\":\"leslin123\",\"user\":{\"id\":8830,\"login\":\"leslin123\",\"avatar_url\":\"http://ruby-china.org/avatar/5964ef3513bd50d5674a3509df957280.png?s=120\"}},{\"id\":17027,\"title\":\"如何定位到网页文章中的某个位置？\",\"created_at\":\"2014-01-27T17:50:28.269+08:00\",\"updated_at\":\"2014-01-28T11:05:31.856+08:00\",\"replied_at\":\"2014-01-28T11:05:31+08:00\",\"replies_count\":4,\"node_name\":\"JavaScript\",\"node_id\":5,\"last_reply_user_id\":175,\"last_reply_user_login\":\"krazy\",\"user\":{\"id\":10846,\"login\":\"levi0214\",\"avatar_url\":\"http://l.ruby-china.org/user/large_avatar/10846.jpg\"}},{\"id\":16925,\"title\":\"[讨论] reverse 一个字符串的顺序.\",\"created_at\":\"2014-01-22T15:21:03.499+08:00\",\"updated_at\":\"2014-01-28T10:55:52.997+08:00\",\"replied_at\":\"2014-01-28T10:55:52+08:00\",\"replies_count\":10,\"node_name\":\"Ruby\",\"node_id\":1,\"last_reply_user_id\":681,\"last_reply_user_login\":\"sevk\",\"user\":{\"id\":1031,\"login\":\"zw963\",\"avatar_url\":\"http://l.ruby-china.org/user/large_avatar/1031.png\"}},{\"id\":17008,\"title\":\"工作也旅行 - 工作机会\",\"created_at\":\"2014-01-26T17:40:16.193+08:00\",\"updated_at\":\"2014-01-28T10:49:41.901+08:00\",\"replied_at\":\"2014-01-28T10:49:41+08:00\",\"replies_count\":40,\"node_name\":\"分享\",\"node_id\":26,\"last_reply_user_id\":1674,\"last_reply_user_login\":\"bigpig85\",\"user\":{\"id\":818,\"login\":\"ichord\",\"avatar_url\":\"http://ruby-china.org/avatar/433967a57ccd76de553af35e01821959.png?s=120\"}},{\"id\":17023,\"title\":\"发送一个 http 请求最少需要多少流量?\",\"created_at\":\"2014-01-27T13:22:56.061+08:00\",\"updated_at\":\"2014-01-28T10:48:03.951+08:00\",\"replied_at\":\"2014-01-28T10:48:03+08:00\",\"replies_count\":21,\"node_name\":\"新手问题\",\"node_id\":52,\"last_reply_user_id\":7822,\"last_reply_user_login\":\"hick\",\"user\":{\"id\":4472,\"login\":\"assyer\",\"avatar_url\":\"http://ruby-china.org/avatar/13986ecfd5a4465e209d85c64968cab8.png?s=120\"}},{\"id\":16906,\"title\":\"精华贴貌似只有 13 页，为嘛有那么多页\",\"created_at\":\"2014-01-21T15:17:06.581+08:00\",\"updated_at\":\"2014-01-28T10:43:47.877+08:00\",\"replied_at\":\"2014-01-28T10:43:47+08:00\",\"replies_count\":1,\"node_name\":\"反馈\",\"node_id\":22,\"last_reply_user_id\":9414,\"last_reply_user_login\":\"jeff_duan\",\"user\":{\"id\":9861,\"login\":\"wangping\",\"avatar_url\":\"http://l.ruby-china.org/user/large_avatar/9861.jpg\"}},{\"id\":17029,\"title\":\"Callbacks 中的一些困惑\",\"created_at\":\"2014-01-27T22:19:22.951+08:00\",\"updated_at\":\"2014-01-28T10:36:11.057+08:00\",\"replied_at\":\"2014-01-28T10:35:28+08:00\",\"replies_count\":5,\"node_name\":\"Rails\",\"node_id\":2,\"last_reply_user_id\":9134,\"last_reply_user_login\":\"dothide\",\"user\":{\"id\":9134,\"login\":\"dothide\",\"avatar_url\":\"http://l.ruby-china.org/user/large_avatar/9134.jpg\"}},{\"id\":16946,\"title\":\"[杭州] ThePlant 招聘\",\"created_at\":\"2014-01-23T14:37:05.580+08:00\",\"updated_at\":\"2014-01-28T10:24:50.097+08:00\",\"replied_at\":\"2014-01-28T10:24:50+08:00\",\"replies_count\":43,\"node_name\":\"招聘\",\"node_id\":25,\"last_reply_user_id\":157,\"last_reply_user_login\":\"raven\",\"user\":{\"id\":562,\"login\":\"Jeweller_Tsai\",\"avatar_url\":\"http://ruby-china.org/avatar/655bd6acdceadb33f1825e65c0d21714.png?s=120\"}},{\"id\":16699,\"title\":\"几本电子书的消息\",\"created_at\":\"2014-01-08T19:49:44.122+08:00\",\"updated_at\":\"2014-01-28T10:17:11.100+08:00\",\"replied_at\":\"2014-01-28T10:17:11+08:00\",\"replies_count\":36,\"node_name\":\"书籍\",\"node_id\":38,\"last_reply_user_id\":1043,\"last_reply_user_login\":\"scriptfans\",\"user\":{\"id\":49,\"login\":\"andor_chen\",\"avatar_url\":\"http://ruby-china.org/avatar/f2672c45fea070e56d4ba4c5e1a6f2b5.png?s=120\"}},{\"id\":17002,\"title\":\"Grunt 中如何引用 bower 安装的库\",\"created_at\":\"2014-01-26T12:03:03.883+08:00\",\"updated_at\":\"2014-01-28T09:51:12.862+08:00\",\"replied_at\":\"2014-01-28T09:51:12+08:00\",\"replies_count\":13,\"node_name\":\"NoPoint\",\"node_id\":61,\"last_reply_user_id\":7629,\"last_reply_user_login\":\"frank_128\",\"user\":{\"id\":5453,\"login\":\"tyaccp_guojian\",\"avatar_url\":\"http://l.ruby-china.org/user/large_avatar/5453.png\"}},{\"id\":16987,\"title\":\"ruby china 和 ruby-china.com 的持有人谈妥了？\",\"created_at\":\"2014-01-25T12:15:10.687+08:00\",\"updated_at\":\"2014-01-28T11:26:58.026+08:00\",\"replied_at\":\"2014-01-28T09:49:21+08:00\",\"replies_count\":6,\"node_name\":\"瞎扯淡\",\"node_id\":27,\"last_reply_user_id\":9173,\"last_reply_user_login\":\"whitebox\",\"user\":{\"id\":9173,\"login\":\"whitebox\",\"avatar_url\":\"http://ruby-china.org/avatar/23cb6305d8a8257990092165041f65f5.png?s=120\"}},{\"id\":17015,\"title\":\"Puma 替换 Unicorn 跑 Gitlab\",\"created_at\":\"2014-01-26T23:45:58.708+08:00\",\"updated_at\":\"2014-01-28T09:45:22.661+08:00\",\"replied_at\":\"2014-01-28T09:45:22+08:00\",\"replies_count\":15,\"node_name\":\"分享\",\"node_id\":26,\"last_reply_user_id\":9821,\"last_reply_user_login\":\"icyleaf\",\"user\":{\"id\":9821,\"login\":\"icyleaf\",\"avatar_url\":\"http://l.ruby-china.org/user/large_avatar/9821.png\"}},{\"id\":17031,\"title\":\"[转]Ruby’s GIL and transactional memory\",\"created_at\":\"2014-01-27T23:37:59.611+08:00\",\"updated_at\":\"2014-01-28T10:07:12.736+08:00\",\"replied_at\":\"2014-01-28T09:43:09+08:00\",\"replies_count\":1,\"node_name\":\"Ruby\",\"node_id\":1,\"last_reply_user_id\":121,\"last_reply_user_login\":\"lyfi2003\",\"user\":{\"id\":4584,\"login\":\"imlcl\",\"avatar_url\":\"http://ruby-china.org/avatar/5d996acd4cb011b445d0039b257462a9.png?s=120\"}},{\"id\":17011,\"title\":\"图片 CSS：怎样才能“响应式+固定宽高比例”？\",\"created_at\":\"2014-01-26T20:37:32.442+08:00\",\"updated_at\":\"2014-01-28T09:54:01.713+08:00\",\"replied_at\":\"2014-01-28T09:36:07+08:00\",\"replies_count\":20,\"node_name\":\"新手问题\",\"node_id\":52,\"last_reply_user_id\":9162,\"last_reply_user_login\":\"cassiuschen\",\"user\":{\"id\":827,\"login\":\"chairy11\",\"avatar_url\":\"http://l.ruby-china.org/user/large_avatar/827.jpg\"}},{\"id\":17018,\"title\":\"ActiveRecord 对于  union  的处理问题\",\"created_at\":\"2014-01-27T09:51:24.572+08:00\",\"updated_at\":\"2014-01-28T09:21:40.376+08:00\",\"replied_at\":\"2014-01-28T09:21:40+08:00\",\"replies_count\":3,\"node_name\":\"Rails\",\"node_id\":2,\"last_reply_user_id\":501,\"last_reply_user_login\":\"TsingHan\",\"user\":{\"id\":501,\"login\":\"TsingHan\",\"avatar_url\":\"http://l.ruby-china.org/user/large_avatar/501.jpeg\"}},{\"id\":17007,\"title\":\"kabam 新年新需求 Ruby engineer 的朋友们，欢迎加入我们。\",\"created_at\":\"2014-01-26T17:08:53.773+08:00\",\"updated_at\":\"2014-01-28T08:42:27.144+08:00\",\"replied_at\":\"2014-01-28T08:42:27+08:00\",\"replies_count\":4,\"node_name\":\"招聘\",\"node_id\":25,\"last_reply_user_id\":10487,\"last_reply_user_login\":\"jablie\",\"user\":{\"id\":2154,\"login\":\"lisa_zhao\",\"avatar_url\":\"http://l.ruby-china.org/user/large_avatar/2154.jpg\"}},{\"id\":16612,\"title\":\"打印出数字 2014，但是代码里不能有任何数字 之 Ruby 版\",\"created_at\":\"2014-01-04T05:28:42.200+08:00\",\"updated_at\":\"2014-01-28T08:36:20.870+08:00\",\"replied_at\":\"2014-01-28T08:36:20+08:00\",\"replies_count\":27,\"node_name\":\"Ruby\",\"node_id\":1,\"last_reply_user_id\":3,\"last_reply_user_login\":\"lgn21st\",\"user\":{\"id\":2847,\"login\":\"Yujing_Z\",\"avatar_url\":\"http://ruby-china.org/avatar/b3041e5120710b3a5859f5af5c6d9ba2.png?s=120\"}},{\"id\":17032,\"title\":\"关于 kindeditor 的保存问题和图片问题\",\"created_at\":\"2014-01-28T01:02:15.357+08:00\",\"updated_at\":\"2014-01-28T08:05:20.666+08:00\",\"replied_at\":null,\"replies_count\":0,\"node_name\":\"新手问题\",\"node_id\":52,\"last_reply_user_id\":null,\"last_reply_user_login\":null,\"user\":{\"id\":10835,\"login\":\"jsasdw1991\",\"avatar_url\":\"http://l.ruby-china.org/user/large_avatar/10835.jpg\"}}]";
    }

    @Override
    public void onRefreshStarted(View view) {
        loadFirstPage();
    }
}

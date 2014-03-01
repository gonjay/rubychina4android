package org.rubychina.app.ui;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.rubychina.app.R;
import org.rubychina.app.model.Topic;
import org.rubychina.app.model.TopicReply;
import org.rubychina.app.ui.adapter.TopicFragmentPagerAdapter;
import org.rubychina.app.ui.fragment.topic.TopicRepliesFragment;
import org.rubychina.app.ui.fragment.topic.TopicReplyFragment;
import org.rubychina.app.ui.fragment.topic.TopicViewFragment;
import org.rubychina.app.utils.ApiParams;
import org.rubychina.app.utils.ApiUtils;
import org.rubychina.app.utils.JsonUtils;
import org.rubychina.app.utils.UserUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 14-3-1.
 */
public class TopicTabActivity extends FragmentActivity implements ActionBar.TabListener {
    private Topic topic = new Topic();

    private final Gson gson = new Gson();

    private ViewPager pager;

    private TopicFragmentPagerAdapter topicFragmentPagerAdapter;

    private List<Fragment> mFragments = new ArrayList<Fragment>();

    List<TopicReply> topicReplies;

    ActionBar actionBar;

    String titles[];

    Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        titles = getResources().getStringArray(R.array.topic_view);

        setContentView(R.layout.activity_topic_detail);

        actionBar = getActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        pager = (ViewPager)findViewById(R.id.pager);

        topicFragmentPagerAdapter = new TopicFragmentPagerAdapter(getSupportFragmentManager(), mFragments);

        pager.setAdapter(topicFragmentPagerAdapter);

        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
                switch (position){
                    case 2:
                        mMenu.findItem(R.id.action_preview).setVisible(true);
                        mMenu.findItem(R.id.action_send).setVisible(true);
                        break;
                    default:
                        mMenu.findItem(R.id.action_preview).setVisible(false);
                        mMenu.findItem(R.id.action_send).setVisible(false);
                        break;
                }
            }
        });

        topic.id = getIntent().getStringExtra("topic_id");

        fetchData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topic_reply_menu, menu);
        this.mMenu = menu;
        mMenu.findItem(R.id.action_refresh).setActionView(R.layout.progressbar);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_preview:
                Intent i = new Intent(TopicTabActivity.this, PreviewActivity.class);
                i.putExtra("body", ((TopicReplyFragment)mFragments.get(2)).getBody());
                startActivity(i);
                break;
            case R.id.action_send:
                if (UserUtils.logined()) {
                    sendReply();
                } else {
                    startActivityForResult(new Intent(TopicTabActivity.this, LoginActivity.class), MainActivity.ACTION_FOR_LOGIN);
                    overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
                }
                break;
            case R.id.action_refresh:
                refresh();
                break;

        }
        return true;
    }

    private void fetchData(){
        ApiUtils.get(String.format(ApiUtils.TOPIC_VIEW, topic.id), null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                topic = topic.getInstance(response);
                mFragments.add(new TopicViewFragment(topic));
                try {
                    String replies = JsonUtils.getString(new JSONObject(response), "replies");
                    Type listType = new TypeToken<List<TopicReply>>() {
                    }.getType();
                    topicReplies = gson.fromJson(replies, listType);
                    mFragments.add(new TopicRepliesFragment(topicReplies));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mFragments.add(new TopicReplyFragment(topic.id));

                topicFragmentPagerAdapter.notifyDataSetChanged();

                for (int i = 0; i < topicFragmentPagerAdapter.getCount(); i++) {
                    actionBar.addTab(
                            actionBar.newTab()
                                    .setText(titles[i])
                                    .setTabListener(TopicTabActivity.this));
                }

                mMenu.findItem(R.id.action_refresh).setActionView(null);

            }
        });
    }

    public void sendReply() {
        String replyBody = ((TopicReplyFragment)mFragments.get(2)).getBody();

        if (replyBody.length() < 1){
            Toast.makeText(TopicTabActivity.this, R.string.reply_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        ApiUtils.post(String.format(ApiUtils.TOPIC_REPLY, topic.id), new ApiParams().with("body", replyBody).withToken(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(TopicTabActivity.this, R.string.reply_success, Toast.LENGTH_SHORT).show();
                TopicReply rt = gson.fromJson(response, TopicReply.class);
                topicReplies.add(rt);
                mFragments.remove(2);
                mFragments.add(2, new TopicReplyFragment(topic.id));
                mFragments.remove(1);
                mFragments.add(1, new TopicRepliesFragment(topicReplies));
                pager.setCurrentItem(1);
            }
        });
    }

    public void setReply(String content){
        pager.setCurrentItem(2);
        ((TopicReplyFragment)mFragments.get(2)).updateBody(content);
    }

    private void refresh(){
        mMenu.findItem(R.id.action_refresh).setActionView(R.layout.progressbar);
        mFragments.clear();
        fetchData();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}

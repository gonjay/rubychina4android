package org.rubychina.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;

import org.rubychina.app.R;
import org.rubychina.app.model.Notification;
import org.rubychina.app.ui.adapter.NotificationAdapter;
import org.rubychina.app.utils.ApiParams;
import org.rubychina.app.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 14-2-11.
 */
public class NotificationActivity extends Activity implements OnDismissCallback {
    private NotificationAdapter adapter;
    private List<Notification> lists = new ArrayList<Notification>();
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_notification);
        mListView = (ListView) findViewById(R.id.listView);
        fetchData();
    }

    private void setSwipeDismissAdapter() {
        adapter = new NotificationAdapter(lists, NotificationActivity.this);
        SwipeDismissAdapter swipeadapter = new SwipeDismissAdapter(adapter, this);
        swipeadapter.setAbsListView(mListView);
        mListView.setAdapter(swipeadapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("position", "" + position);
                Intent i = new Intent(NotificationActivity.this, TopicTabActivity.class);
                i.putExtra("topic_id",lists.get(position).getMention().getID());
                startActivity(i);
            }
        });
    }

    private void fetchData(){
        ApiUtils.get(ApiUtils.NOTIFICATIONS, new ApiParams().withToken(), new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String responce) {
                lists = new Gson().fromJson(responce, new TypeToken<ArrayList<Notification>>(){}.getType());
                setSwipeDismissAdapter();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDismiss(@NonNull ViewGroup listView, @NonNull int[] ints) {
        Log.v("onDismiss:", " " + ints);
        if (lists.size() < 1){
            return;
        }
        for (int position : ints) {
            adapter.remove(position);
        }
    }
}

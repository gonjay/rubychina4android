package org.rubychina.app.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.haarman.listviewanimations.itemmanipulation.SwipeDismissAdapter;
import com.haarman.listviewanimations.itemmanipulation.contextualundo.ContextualUndoAdapter;
import com.haarman.listviewanimations.itemmanipulation.contextualundo.ContextualUndoAdapter.DeleteItemCallback;
import com.loopj.android.http.AsyncHttpResponseHandler;

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
public class NotificationActivity extends Activity implements DeleteItemCallback, OnDismissCallback {
    private NotificationAdapter adapter;
    private List<Notification> lists = new ArrayList<Notification>();
    ContextualUndoAdapter undoAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
//        setSwipeDismissAdapter();
        mListView = (ListView) findViewById(R.id.listView);
        fetchData();
    }

    private void setSwipeDismissAdapter() {
        adapter = new NotificationAdapter(lists, NotificationActivity.this);
        SwipeDismissAdapter swipeadapter = new SwipeDismissAdapter(adapter, this);
        swipeadapter.setAbsListView(getListView());
        getListView().setAdapter(swipeadapter);
    }

    protected ListView getListView(){
        return mListView;
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

    public void setDis(){
        adapter = new NotificationAdapter(lists, NotificationActivity.this);
        undoAdapter = new ContextualUndoAdapter(adapter, R.layout.undo_row, R.id.undo_row_undobutton);
        undoAdapter.setAbsListView(getListView());
        getListView().setAdapter(undoAdapter);
        undoAdapter.setDeleteItemCallback(this);
    }

    @Override
    public void deleteItem(int position) {
        Log.v("deleteItem:", " " + position);
        adapter.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDismiss(AbsListView absListView, int[] ints) {
        Log.v("onDismiss:", " " + ints);
        for (int position : ints) {
            Log.v("onDismiss:", " " + position);
            adapter.remove(position);
        }
    }

    private class MyFormatCountDownCallback implements ContextualUndoAdapter.CountDownFormatter {

        @Override
        public String getCountDownString(long millisUntilFinished) {
            int seconds = (int) Math.ceil((millisUntilFinished / 1000.0));

            if (seconds > 0) {
                return getResources().getQuantityString(R.plurals.countdown_seconds, seconds, seconds);
            }
            return getString(R.string.countdown_dismissing);
        }
    }

}

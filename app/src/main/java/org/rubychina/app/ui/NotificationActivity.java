package org.rubychina.app.ui;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.haarman.listviewanimations.ArrayAdapter;
import com.haarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.haarman.listviewanimations.itemmanipulation.contextualundo.ContextualUndoAdapter;
import com.haarman.listviewanimations.itemmanipulation.contextualundo.ContextualUndoAdapter.DeleteItemCallback;

import org.rubychina.app.R;

import java.util.ArrayList;

/**
 * Created by mac on 14-2-11.
 */
public class NotificationActivity extends ListActivity implements OnDismissCallback, DeleteItemCallback {
    private ArrayAdapter<Integer> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = createListAdapter();

        setContextualUndoWithTimedDeleteAndCountDownAdapter();

    }

    public static ArrayList<Integer> getItems() {
        ArrayList<Integer> items = new ArrayList<Integer>();
        for (int i = 0; i < 1000; i++) {
            items.add(i);
        }
        return items;
    }

    protected ArrayAdapter<Integer> createListAdapter() {
        return new MyListAdapter(this, getItems());
    }

    @Override
    public void deleteItem(int position) {

    }

    @Override
    public void onDismiss(AbsListView absListView, int[] ints) {

    }

    private void setContextualUndoWithTimedDeleteAndCountDownAdapter() {
        ContextualUndoAdapter adapter = new ContextualUndoAdapter(mAdapter, R.layout.undo_row, R.id.undo_row_undobutton, 3000, R.id.undo_row_texttv, new MyFormatCountDownCallback());
        adapter.setAbsListView(getListView());
        getListView().setAdapter(adapter);
        adapter.setDeleteItemCallback(this);
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

    private static class MyListAdapter extends ArrayAdapter<Integer> {

        private Context mContext;

        public MyListAdapter(Context context, ArrayList<Integer> items) {
            super(items);
            mContext = context;
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).hashCode();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = (TextView) convertView;
            if (tv == null) {
                tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.list_row, parent, false);
            }
            tv.setText("This is row number " + getItem(position));
            return tv;
        }
    }
}

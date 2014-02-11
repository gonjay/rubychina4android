package org.rubychina.app.ui;

import android.app.ListActivity;
import android.widget.AbsListView;
import android.widget.ListView;

import com.haarman.listviewanimations.ArrayAdapter;
import com.haarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.haarman.listviewanimations.itemmanipulation.contextualundo.ContextualUndoAdapter.DeleteItemCallback;

/**
 * Created by mac on 14-2-11.
 */
public abstract class NotificationActivity extends ListActivity implements OnDismissCallback, DeleteItemCallback {
    private ArrayAdapter<Integer> mAdapter;

    public NotificationActivity(ArrayAdapter<Integer> mAdapter) {
        this.mAdapter = mAdapter;
    }

    @Override
    public void deleteItem(int position) {

    }

    @Override
    public void onDismiss(AbsListView absListView, int[] ints) {

    }
}

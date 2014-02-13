package org.rubychina.app.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.rubychina.app.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 14-2-13.
 */
public class TopUsersAdapter extends MyBaseAdapter {
    List<User> users = new ArrayList<User>();

    public TopUsersAdapter(Context context) {
        super(context);
    }
    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        return convertView;
    }

}

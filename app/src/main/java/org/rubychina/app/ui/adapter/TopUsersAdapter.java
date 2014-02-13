package org.rubychina.app.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.rubychina.app.model.User;

import java.util.ArrayList;
import java.util.List;

import org.rubychina.app.R;
import org.rubychina.app.ui.ProfileActivity;

/**
 * Created by mac on 14-2-13.
 */
public class TopUsersAdapter extends MyBaseAdapter {
    List<User> users = new ArrayList<User>();

    public TopUsersAdapter(Context context, List<User> users) {
        super(context);
        this.users = users;
    }
    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View v = convertView;

        if (v == null){
            v = mInflater.inflate(R.layout.item_user, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.avatar = (ImageView)v.findViewById(R.id.iv_avatar);
            viewHolder.name = (TextView)v.findViewById(R.id.tv_name);
            viewHolder.bio = (TextView)v.findViewById(R.id.tv_bio);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.name.setText(users.get(position).getName());
        viewHolder.bio.setText(users.get(position).getBrief());

        imageLoader.displayImage(users.get(position).avatar_url, viewHolder.avatar, options);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProfileActivity.class);
                i.putExtra("user", users.get(position).login);
                context.startActivity(i);
                ((Activity)context).overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        });

        return v;
    }

    private class ViewHolder{
        ImageView avatar;
        TextView name, bio;
    }

}

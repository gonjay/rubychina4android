package org.rubychina.app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.rubychina.app.R;
import org.rubychina.app.model.Notification;
import org.rubychina.app.ui.TopicTabActivity;
import org.rubychina.app.utils.ApiUtils;
import org.rubychina.app.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 14-2-11.
 */
public class NotificationAdapter extends MyBaseAdapter {

    private List<Notification> notifications = new ArrayList<Notification>();

    public NotificationAdapter(List<Notification> notifications, Context context){
        super(context);
        this.notifications = notifications;
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            view = mInflater.inflate(R.layout.item_notification, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)view.findViewById(R.id.tv_title);
            viewHolder.body = (TextView)view.findViewById(R.id.tv_body);
            viewHolder.avatar = (ImageView)view.findViewById(R.id.iv_avatar);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.title.setText(notifications.get(position).getMention().getTitle().replace("null", ""));
        viewHolder.body.setText(notifications.get(position).getMention().body);
        imageLoader.displayImage(notifications.get(position).getMention().user.avatar_url, viewHolder.avatar, options);

        viewHolder.body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("getID: ", notifications.get(position).getMention().getID());
                Intent i = new Intent(context, TopicTabActivity.class);
                i.putExtra("topic_id",notifications.get(position).getMention().getID());
                context.startActivity(i);
            }
        });

        return view;
    }

    public void remove(final int position) {
        notifications.remove(position);
        NotificationAdapter.this.notifyDataSetChanged();
        ApiUtils.delete(String.format(ApiUtils.NOTIFICATION_DELETE, notifications.get(position).id,
                UserUtils.getUserToken()), new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String responce){
                Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Throwable e, String responce){
                Toast.makeText(context, R.string.delete_failed, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private static class ViewHolder {
        TextView title, body;
        ImageView avatar;
    }
}

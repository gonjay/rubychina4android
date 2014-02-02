package org.rubychina.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.rubychina.app.MyApp;
import org.rubychina.app.R;
import org.rubychina.app.model.TopicReply;

import java.util.List;

/**
 * Created by mac on 14-2-2.
 */
public class TopicReplyAdapter extends TopicAdapter {
    private List<TopicReply> replies;

    public TopicReplyAdapter(List<TopicReply> topics, Context context) {
        this.replies = topics;
        this.context = context;
    }

    @Override
    public int getCount() {
        return replies == null ? 0 : replies.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder = new ViewHolder();

        convertView = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.reply_item, null);

        holder.avatar = (ImageView)convertView.findViewById(R.id.iv_avatar);
        holder.time = (TextView)convertView.findViewById(R.id.tv_time);
        holder.body = (TextView)convertView.findViewById(R.id.tv_body);
        holder.userName = (TextView)convertView.findViewById(R.id.tv_login);

        holder.body.setText(replies.get(position).body);
        holder.userName.setText(replies.get(position).user.login);
        holder.time.setText(replies.get(position).getReplyBrief(position+1));

        imageLoader.displayImage(replies.get(position).user.avatar_url, holder.avatar, options);

        return convertView;
    }
}

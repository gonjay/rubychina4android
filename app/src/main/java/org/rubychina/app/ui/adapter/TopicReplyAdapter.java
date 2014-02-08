package org.rubychina.app.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.rubychina.app.MyApp;
import org.rubychina.app.R;
import org.rubychina.app.model.Topic;
import org.rubychina.app.model.TopicReply;
import org.rubychina.app.ui.TopicActivity;

import java.util.List;

/**
 * Created by mac on 14-2-2.
 */
public class TopicReplyAdapter extends TopicAdapter {
    private List<TopicReply> replies;
    AlertDialog alertDialog;

    AlertDialog.Builder builder;

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
        holder.item = (RelativeLayout)convertView.findViewById(R.id.rl_item);

        holder.body.setText(replies.get(position).body);
        holder.userName.setText(replies.get(position).user.login);
        holder.time.setText(replies.get(position).getReplyBrief(position));

        imageLoader.displayImage(replies.get(position).user.avatar_url, holder.avatar, options);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TopicActivity ta = (TopicActivity)context;
//                ta.setReply(replies.get(position).getReplyFront(position));
                createDialog(replies.get(position).getReplyFront(position));
            }
        });

        return convertView;
    }

    private void createDialog(final String replyFront){
        builder = new AlertDialog.Builder(context);
        builder.setTitle("");
        builder.setItems(R.array.repley_choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        TopicActivity ta = (TopicActivity)context;
                        ta.setReply(replyFront);
                        break;
                    case 1:
                        break;
                    default:
                        break;
                }
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }
}

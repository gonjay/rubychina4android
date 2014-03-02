package org.rubychina.app.ui.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.rubychina.app.R;
import org.rubychina.app.helper.CustomLinkMovementMethod;
import org.rubychina.app.helper.ImageGetter;
import org.rubychina.app.model.TopicReply;
import org.rubychina.app.ui.ProfileActivity;
import org.rubychina.app.ui.TopicTabActivity;

import java.util.List;

/**
 * Created by mac on 14-2-2.
 */
public class TopicReplyAdapter extends TopicAdapter {
    private List<TopicReply> replies;
    AlertDialog alertDialog;

    AlertDialog.Builder builder;

    LayoutInflater mInflater;

    public TopicReplyAdapter(List<TopicReply> topics, Context context) {
        this.replies = topics;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return replies == null ? 0 : replies.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder = new ViewHolder();

        convertView = mInflater.inflate(R.layout.reply_item, null);

        holder.avatar = (ImageView)convertView.findViewById(R.id.iv_avatar);
        holder.time = (TextView)convertView.findViewById(R.id.tv_time);
        holder.body = (TextView)convertView.findViewById(R.id.tv_body);
        holder.userName = (TextView)convertView.findViewById(R.id.tv_login);
        holder.item = (RelativeLayout)convertView.findViewById(R.id.rl_item);

        holder.body.setText(Html.fromHtml(replies.get(position).body_html, new ImageGetter(holder.body, context), null));

        holder.body.setMovementMethod(CustomLinkMovementMethod.getInstance(context));

        holder.userName.setText(replies.get(position).user.login);
        holder.time.setText(replies.get(position).getReplyBrief(position));

        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProfileActivity.class);
                i.putExtra("user", replies.get(position).user.login);
                context.startActivity(i);
                ((Activity)context).overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        });

        imageLoader.displayImage(replies.get(position).user.avatar_url, holder.avatar, options);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        TopicTabActivity ta = (TopicTabActivity)context;
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

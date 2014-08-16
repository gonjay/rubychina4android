package org.rubychina.app.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.rubychina.app.MyApp;
import org.rubychina.app.R;
import org.rubychina.app.model.Topic;
import org.rubychina.app.ui.ProfileActivity;
import org.rubychina.app.ui.TopicTabActivity;

import java.util.List;

/**
 * Created by mac on 14-1-28.
 */
public class TopicAdapter extends BaseAdapter {
    private List<Topic> topics;
    public Context context;
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnFail(R.drawable.avatar).displayer(new RoundedBitmapDisplayer(100))
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
            .cacheInMemory(true)
            .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

    public class ViewHolder{
        public ImageView avatar;
        public TextView title, time, replies, node, userName, body;
        public RelativeLayout item;
        public List<String> images;
    }

    public TopicAdapter(){

    }

    public TopicAdapter(List<Topic> topics, Context context){
        this.topics = topics;
        this.context = context;
    }

    @Override
    public int getCount() {
        return topics == null ? 0 : topics.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;

        if (view == null) {

            view = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            view.setTag(holder);

            holder.avatar = (ImageView) view.findViewById(R.id.avatar);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.node = (TextView) view.findViewById(R.id.nodeName);
            holder.time = (TextView) view.findViewById(R.id.time);
            holder.replies = (TextView) view.findViewById(R.id.text_comment_count);
            holder.userName = (TextView) view.findViewById(R.id.userName);

            holder.title.setText(topics.get(position).title);
            holder.node.setText(topics.get(position).node_name);
            holder.time.setText(topics.get(position).getLastReply());
            // 显示回复条数
            holder.replies.setText(topics.get(position).replies_count);

            holder.userName.setText(topics.get(position).user.login);
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, TopicTabActivity.class);
                    i.putExtra("topic_id", topics.get(position).id);
                    context.startActivity(i);
                }
            });

            holder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ProfileActivity.class);
                    i.putExtra("user", topics.get(position).user.login);
                    context.startActivity(i);
                    ((Activity) context).overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                }
            });

            imageLoader.displayImage(topics.get(position).user.avatar_url, holder.avatar, options);
        } else {

            holder = (ViewHolder)view.getTag();

        }

        return view;
    }
}

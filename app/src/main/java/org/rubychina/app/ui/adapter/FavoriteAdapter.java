package org.rubychina.app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.rubychina.app.R;
import org.rubychina.app.model.Topic;
import org.rubychina.app.ui.TopicTabActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 14-2-10.
 */
public class FavoriteAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context context;
    private List<Topic> topics = new ArrayList<Topic>();

    public FavoriteAdapter(List<Topic> nodes, Context context){
        this.topics = nodes;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return topics.size();
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
    public View getView(final int position, View v, ViewGroup parent) {
        v = mInflater.inflate(R.layout.fav_item, parent, false);
        ((TextView)v.findViewById(R.id.tv_node)).setText(topics.get(position).node_name);
        ((TextView)v.findViewById(R.id.tv_title)).setText(topics.get(position).title);
        // 有人回复时才显示回复数量
        if (Integer.valueOf(topics.get(position).replies_count) != 0) {
            ((TextView)v.findViewById(R.id.tv_count)).setText(topics.get(position).replies_count);
        } else {
            ((TextView)v.findViewById(R.id.tv_count)).setText("");
        }
        ((RelativeLayout)v.findViewById(R.id.rl_item)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topics.get(position).id != null){
                    Intent i = new Intent(context, TopicTabActivity.class);
                    i.putExtra("topic_id",topics.get(position).id);
                    context.startActivity(i);
                }
            }
        });
        return v;
    }
}

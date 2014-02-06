package org.rubychina.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etsy.android.grid.util.DynamicHeightTextView;

import org.rubychina.app.R;
import org.rubychina.app.model.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 14-2-6.
 */
public class NodeAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<Node> nodes = new ArrayList<Node>();

    public NodeAdapter(Context context, List<Node> nodes){
        this.nodes = nodes;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return nodes.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.item_nodes, parent, false);
        ((TextView)convertView.findViewById(R.id.tv_name)).setText(nodes.get(position).name);
        ((TextView)convertView.findViewById(R.id.tv_summary)).setText(nodes.get(position).summary);
        return convertView;
    }
}

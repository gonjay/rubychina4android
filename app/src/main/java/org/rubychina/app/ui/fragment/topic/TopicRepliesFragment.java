package org.rubychina.app.ui.fragment.topic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.rubychina.app.R;
import org.rubychina.app.model.Topic;
import org.rubychina.app.model.TopicReply;
import org.rubychina.app.ui.adapter.TopicReplyAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 14-2-2.
 */
public class TopicRepliesFragment extends Fragment{
    private ListView mListView;
    private TopicReplyAdapter mAdapter;
    private Type listType = new TypeToken<List<Topic>>(){}.getType();

    public List<TopicReply> topicReplies = new ArrayList<TopicReply>();

    Gson gson = new Gson();

    public static TopicRepliesFragment newInstance(String repliesJson){
        TopicRepliesFragment fragment = new TopicRepliesFragment();
        Bundle args = new Bundle();
        args.putString("repliesJson", repliesJson);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String repliesJson = getArguments().getString("repliesJson");
            Type listType = new TypeToken<List<TopicReply>>() {
            }.getType();
            topicReplies = gson.fromJson(repliesJson, listType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_replies, container, false);
        mListView = (ListView)view.findViewById(R.id.listView);
        mAdapter = new TopicReplyAdapter(topicReplies, getActivity());
        mListView.setAdapter(mAdapter);
        return view;
    }

    public void updateData(List<TopicReply> topicReplies){
        this.topicReplies = topicReplies;
    }

    public void updateView() {
        mAdapter.notifyDataSetChanged();
    }
}

package org.rubychina.app.ui.fragment.Topic;

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
import org.rubychina.app.ui.adapter.TopicAdapter;
import org.rubychina.app.ui.adapter.TopicReplyAdapter;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by mac on 14-2-2.
 */
public class TopicRepliesFragment extends Fragment{
    private ListView mListView;
    private TopicReplyAdapter mAdapter;
    Gson gson = new Gson();
    Type listType = new TypeToken<List<Topic>>(){}.getType();

    private List<TopicReply> topicReplies;

    public TopicRepliesFragment(List<TopicReply> topicReplies) {
        this.topicReplies = topicReplies;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        mListView = (ListView)view.findViewById(R.id.listView);
        mAdapter = new TopicReplyAdapter(topicReplies, getActivity());
        mListView.setAdapter(mAdapter);
        return view;
    }
}

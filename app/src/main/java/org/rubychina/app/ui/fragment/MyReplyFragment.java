package org.rubychina.app.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mac on 14-2-2.
 */
public class MyReplyFragment extends Fragment {
    public static final String ARG_TOPIC_ID = "topic_id";
    public static final String ARG_NODE_ID = "node_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_TOPIC_ID)) {

        } else if (getArguments().containsKey(ARG_NODE_ID)){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return null;
    }
}

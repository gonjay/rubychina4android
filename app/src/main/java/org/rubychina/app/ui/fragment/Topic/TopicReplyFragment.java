package org.rubychina.app.ui.fragment.topic;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.rubychina.app.R;
import org.rubychina.app.ui.TopicActivity;
import org.rubychina.app.utils.ApiParams;
import org.rubychina.app.utils.ApiUtils;

/**
 * Created by mac on 14-2-2.
 */
public class TopicReplyFragment extends Fragment {
    public static final String ARG_TOPIC_ID = "topic_id";
    public static final String ARG_NODE_ID = "node_id";

    private String topic_id;

    private EditText body;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_TOPIC_ID)) {
            topic_id = getArguments().getString(ARG_TOPIC_ID);
        } else if (getArguments().containsKey(ARG_NODE_ID)) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_reply, container, false);

        body = (EditText) view.findViewById(R.id.et_reply_body);

        return view;
    }

    public void sendReply() {
        String replyBody = body.getText().toString();
        if (replyBody.length() < 1){
            Toast.makeText(getActivity(), R.string.reply_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        ApiUtils.post(ApiUtils.TOPIC_REPLY + topic_id + "/replies.json", new ApiParams().with("body", replyBody).withToken(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(getActivity(), R.string.reply_success, Toast.LENGTH_SHORT).show();
                TopicActivity ta = (TopicActivity)getActivity();
                ta.afterReply();
                clearBody();
            }
        });
    }

    public void clearBody(){
        body.setText("");
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(body.getWindowToken(), 0);
    }

    public String getBody(){
        return body.getText().toString();
    }

}

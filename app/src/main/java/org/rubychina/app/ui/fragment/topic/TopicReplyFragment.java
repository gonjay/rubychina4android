package org.rubychina.app.ui.fragment.topic;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.rubychina.app.R;

/**
 * Created by mac on 14-2-2.
 */
public class TopicReplyFragment extends Fragment {
    private EditText body;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_reply, container, false);

        body = (EditText) view.findViewById(R.id.et_reply_body);

        return view;
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

    public void updateBody(String content) {
        if (body.getText().toString().length() > 0 && body.getText().toString().contains("@")){
            content = body.getText().toString() + "\n" + content;
        }

        body.setText(content);
    }
}

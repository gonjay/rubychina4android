package org.rubychina.app.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.rubychina.app.R;
import org.rubychina.app.model.Node;
import org.rubychina.app.model.Topic;
import org.rubychina.app.ui.TopicTabActivity;
import org.rubychina.app.utils.ApiParams;
import org.rubychina.app.utils.ApiUtils;
import org.rubychina.app.utils.JsonUtils;

/**
 * Created by mac on 14-2-4.
 */
public class NewTopicFragment extends Fragment {
    private Spinner spinner1, spinner2;

    private EditText body, title;

    private ArrayAdapter<CharSequence> adapter;

    String s[][] = Node.nodes;

    int sp1, sp2;

    String node_id;

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_new_topic, container, false);
        findView();
        bindView();
        return v;
    }

    public String getBody(){
        return body.getText().toString();
    }

    private void sendTopic(){

    }

    private void bindView() {
        adapter =new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, s[7]);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp1 = position;
                adapter =new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, s[position]);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp2 = position;
                node_id = Node.nodes_id[sp1][sp2] + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void findView() {
        spinner1 = (Spinner) v.findViewById(R.id.spinner1);
        spinner2 = (Spinner) v.findViewById(R.id.spinner2);
        body = (EditText) v.findViewById(R.id.et_body);
        title = (EditText) v.findViewById(R.id.et_title);
    }

    private final Gson gson = new Gson();

    public void send() {
        ApiUtils.post(ApiUtils.TOPIC_NEW, new ApiParams()
                .with("node_id", node_id)
                .with("title", title.getText().toString())
                .with("body", body.getText().toString())
                .withToken(),
                new AsyncHttpResponseHandler(){
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getActivity(), R.string.send_success, Toast.LENGTH_SHORT).show();
                        Topic t = gson.fromJson(response, Topic.class);
                        Intent i = new Intent(getActivity(), TopicTabActivity.class);
                        i.putExtra("topic_id", t.id);
                        getActivity().startActivity(i);
                        getActivity().finish();
                    }

                    @Override
                    public  void onFailure(java.lang.Throwable error, java.lang.String response){
                        try {
                            String s1 = JsonUtils.getString(new JSONObject(response), "error");
                            Toast.makeText(getActivity(), s1.replace(",", "\n").replace("\"", "").replace("[",""), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

        });

    }
}

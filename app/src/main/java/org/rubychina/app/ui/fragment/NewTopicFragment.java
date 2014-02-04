package org.rubychina.app.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.rubychina.app.R;
import org.rubychina.app.model.Node;

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
    }
}

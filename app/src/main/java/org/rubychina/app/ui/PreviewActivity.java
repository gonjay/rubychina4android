package org.rubychina.app.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import org.rubychina.app.R;
import org.rubychina.app.ui.fragment.PreviewFragment;

/**
 * Created by mac on 14-2-4.
 */
public class PreviewActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_topic);
        String body = getIntent().getStringExtra("body");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PreviewFragment(body))
                    .commit();
        }
    }
}

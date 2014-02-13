package org.rubychina.app.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import org.rubychina.app.R;
import org.rubychina.app.ui.fragment.PreferenceFragment;

/**
 * Created by mac on 14-2-13.
 */
public class PreferenceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_topic);
        getFragmentManager().beginTransaction().replace(R.id.container, new PreferenceFragment())
                .commit();

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            overridePendingTransition(R.anim.push_down_in,R.anim.push_down_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

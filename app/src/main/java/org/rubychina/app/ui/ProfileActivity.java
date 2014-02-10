package org.rubychina.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import org.rubychina.app.R;
import org.rubychina.app.ui.fragment.ProfileFragment;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

/**
 * Created by mac on 14-2-10.
 */
public class ProfileActivity extends FragmentActivity {
    private PullToRefreshAttacher mPullToRefreshAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_new_topic);
        initPulltorefresh();
        String user = "assyer";
        if(getIntent().getExtras().getString("user") != null){
            user = getIntent().getExtras().getString("user");
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ProfileFragment(user))
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
            return;
        }
        finish();
        overridePendingTransition(R.anim.anim_left_to_right, R.anim.anim_right_to_left);
    }

    private void initPulltorefresh(){
        PullToRefreshAttacher.Options options = new PullToRefreshAttacher.Options();
        options.headerInAnimation = R.anim.pulldown_fade_in;
        options.headerOutAnimation = R.anim.pulldown_fade_out;
        options.refreshScrollDistance = 0.3f;
        options.headerLayout = R.layout.pulldown_header;
        mPullToRefreshAttacher = new PullToRefreshAttacher(this, options);
    }

    public PullToRefreshAttacher getPullToRefreshAttacher() {
        return mPullToRefreshAttacher;
    }
}

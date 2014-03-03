package org.rubychina.app.ui;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.rubychina.app.R;
import org.rubychina.app.helper.WebSocketService;
import org.rubychina.app.ui.fragment.DrawerFragment;
import org.rubychina.app.ui.fragment.topic.TopicsFragment;
import org.rubychina.app.utils.ApiUtils;
import org.rubychina.app.utils.UserUtils;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

public class MainActivity extends FragmentActivity {
    public static final int ACTION_FOR_LOGIN = 2000;
    public static final String TOPICS = "topics";
    public static final String ACTIVITY_EXTRA = "activity";

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu mMenu;
    private PullToRefreshAttacher mPullToRefreshAttacher;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, WebSocketService.class);
        startService(intent);

        if (getIntent().getStringExtra(ACTIVITY_EXTRA) != null) {
            startActivity(new Intent(this, NotificationActivity.class));
        }

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.ic_actionbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerLayout.setScrimColor(Color.argb(100, 0, 0, 0));

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                mMenu.findItem(R.id.action_write).setVisible(true);
            }

            public void onDrawerOpened(View drawerView) {
                mMenu.findItem(R.id.action_write).setVisible(false);

            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        PullToRefreshAttacher.Options options = new PullToRefreshAttacher.Options();
        options.headerInAnimation = R.anim.pulldown_fade_in;
        options.headerOutAnimation = R.anim.pulldown_fade_out;
        options.refreshScrollDistance = 0.3f;
        options.headerLayout = R.layout.pulldown_header;
        mPullToRefreshAttacher = new PullToRefreshAttacher(this, options);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new TopicsFragment("topics", ApiUtils.TOPICS, mPullToRefreshAttacher))
                    .commit();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.left_drawer, new DrawerFragment()).commit();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        mMenu = menu;
        if (!UserUtils.logined()) mMenu.findItem(R.id.action_exit).setTitle(R.string.login);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, PreferenceActivity.class));
            overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
        } else if (id == R.id.action_write){

            if (UserUtils.logined()) {
                startActivity(new Intent(MainActivity.this, NewTopicActivity.class));
            } else {
                startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), ACTION_FOR_LOGIN);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        } else if (id == R.id.action_exit) {
            if (UserUtils.logined()){
                this.invalidateOptionsMenu();
                UserUtils.clearUser();
                getSupportFragmentManager().beginTransaction().replace(R.id.left_drawer, new DrawerFragment()).commit();
            } else {
                startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), ACTION_FOR_LOGIN);
                overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public PullToRefreshAttacher getPullToRefreshAttacher() {
        return mPullToRefreshAttacher;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == LoginActivity.LOGIN_SUCCESS){
            this.invalidateOptionsMenu();
            getSupportFragmentManager().beginTransaction().replace(R.id.left_drawer, new DrawerFragment()).commit();
        }
    }

    public void setMainContent(Fragment fragment){
        mDrawerLayout.closeDrawers();
        //You need to pop some stacks in case setMainContent called after addMainContent
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void addMainContent(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).addToBackStack(null)
                .commit();
    }

}

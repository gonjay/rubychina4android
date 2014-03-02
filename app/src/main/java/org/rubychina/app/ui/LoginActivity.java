package org.rubychina.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.rubychina.app.R;
import org.rubychina.app.model.User;
import org.rubychina.app.ui.fragment.LoginFragment;
import org.rubychina.app.ui.fragment.SignUpFragment;
import org.rubychina.app.ui.fragment.topic.TopicsFragment;
import org.rubychina.app.utils.ApiParams;
import org.rubychina.app.utils.ApiUtils;
import org.rubychina.app.utils.JsonUtils;
import org.rubychina.app.utils.UserUtils;

/**
 * Created by mac on 14-1-29.
 */
public class LoginActivity extends FragmentActivity {
    public static final int LOGIN_SUCCESS = 2001;

    MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        this.item = menu.findItem(R.id.action_sign_up);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_sign_up:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new SignUpFragment())
                        .addToBackStack(null)
                        .commit();
                item.setVisible(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() < 1){
            item.setVisible(true);
        }
        overridePendingTransition(R.anim.push_down_in,R.anim.push_down_out);
    }
}

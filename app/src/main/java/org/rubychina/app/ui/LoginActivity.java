package org.rubychina.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
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
import org.rubychina.app.utils.ApiParams;
import org.rubychina.app.utils.ApiUtils;
import org.rubychina.app.utils.JsonUtils;
import org.rubychina.app.utils.UserUtils;

/**
 * Created by mac on 14-1-29.
 */
public class LoginActivity extends Activity {
    public static final int LOGIN_SUCCESS = 2001;

    private String login;
    private String password;
    private EditText loginEditText, passwordEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        loginEditText = (EditText)findViewById(R.id.et_user_login);
        passwordEditText = (EditText)findViewById(R.id.et_user_password);
        submitButton = (Button)findViewById(R.id.bt_summit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = loginEditText.getText().toString();
                password = passwordEditText.getText().toString();
                if (login.length() > 0 && password.length() > 0){
                    signIn();
                } else {
                    Toast.makeText(LoginActivity.this, R.string.login_validate,Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void signIn() {
        ApiUtils.post(ApiUtils.SIGN_IN, new ApiParams().with("user[login]", login).with("user[password]", password), new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String responce) {
                System.out.println("responce: "+responce);
                final Gson gson = new Gson();
                User u = gson.fromJson(responce, User.class);
                UserUtils.saveUserLogin(u.login);
                UserUtils.saveUserToken(u.private_token);
                UserUtils.saveUserEmail(u.email);
                Toast.makeText(LoginActivity.this, R.string.login_success,Toast.LENGTH_SHORT).show();
                ApiUtils.get(ApiUtils.USER_PROFILE+u.login+".json",null,new AsyncHttpResponseHandler(){
                    @Override
                    public void onSuccess(String responce) {
                        User userPro = gson.fromJson(responce, User.class);
                        UserUtils.saveUserAvatar(userPro.avatar_url);
                        setResult(LOGIN_SUCCESS);
                        onBackPressed();
                    }
                });
            }

            @Override
            public void onFailure(Throwable error, String responce) {
                String notice = "未知错误";
                try {
                    notice = JsonUtils.getString(new JSONObject(responce), "error");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(LoginActivity.this, notice,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.push_down_in,R.anim.push_down_out);
    }
}

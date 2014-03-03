package org.rubychina.app.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.rubychina.app.R;
import org.rubychina.app.helper.WebSocketService;
import org.rubychina.app.model.User;
import org.rubychina.app.utils.ApiParams;
import org.rubychina.app.utils.ApiUtils;
import org.rubychina.app.utils.JsonUtils;
import org.rubychina.app.utils.UserUtils;

/**
 * Created by mac on 14-3-2.
 */
public class LoginFragment extends Fragment{
    public static final int LOGIN_SUCCESS = 2001;

    private String login;
    private String password;
    private EditText loginEditText, passwordEditText;
    private Button submitButton;

    private final Gson gson = new Gson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_login, null);

        loginEditText = (EditText)v.findViewById(R.id.et_user_login);
        passwordEditText = (EditText)v.findViewById(R.id.et_user_password);
        submitButton = (Button)v.findViewById(R.id.bt_summit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = loginEditText.getText().toString();
                password = passwordEditText.getText().toString();
                if (login.length() > 0 && password.length() > 0){
                    signIn();
                } else {
                    Toast.makeText(getActivity(), R.string.login_validate, Toast.LENGTH_LONG).show();
                }

            }
        });

        return v;
    }


    private void signIn() {
        ApiUtils.post(ApiUtils.SIGN_IN, new ApiParams().with("user[login]", login).with("user[password]", password), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String responce) {
                User u = gson.fromJson(responce, User.class);
                UserUtils.saveUserLogin(u.login);
                UserUtils.saveUserToken(u.private_token);
                UserUtils.saveUserEmail(u.email);
                Toast.makeText(getActivity(), R.string.login_success, Toast.LENGTH_SHORT).show();

                ApiUtils.get(String.format(ApiUtils.USER_PROFILE, u.login), null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String responce) {
                        User userPro = gson.fromJson(responce, User.class);
                        UserUtils.saveUserAvatar(userPro.avatar_url);
                        getActivity().setResult(LOGIN_SUCCESS);
                        getActivity().onBackPressed();
                    }
                });

                ApiUtils.get(ApiUtils.USER_TEMP_ACCESS_TOKEN, new ApiParams().withToken(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String responce) {

                        Log.v("", responce);

                        try {
                            String temp_access_token = JsonUtils.getString(new JSONObject(responce), "temp_access_token");
                            UserUtils.saveUserTempToken(temp_access_token);
                            Intent intent = new Intent(getActivity(), WebSocketService.class);
                            getActivity().startService(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                Toast.makeText(getActivity(), notice, Toast.LENGTH_LONG).show();
            }
        });
    }
}

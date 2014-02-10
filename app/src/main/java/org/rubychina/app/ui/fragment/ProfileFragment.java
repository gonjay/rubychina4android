package org.rubychina.app.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONException;
import org.json.JSONObject;
import org.rubychina.app.R;
import org.rubychina.app.helper.MyBitmapDisplayer;
import org.rubychina.app.model.Topic;
import org.rubychina.app.model.User;
import org.rubychina.app.ui.ProfileActivity;
import org.rubychina.app.ui.adapter.FavoriteAdapter;
import org.rubychina.app.utils.ApiUtils;
import org.rubychina.app.utils.JsonUtils;

import java.util.List;

/**
 * Created by mac on 14-2-10.
 */
public class ProfileFragment extends Fragment {
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnFail(R.drawable.avatar).displayer(new MyBitmapDisplayer(200, 2000))
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
            .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

    View view;
    String userName;
    ImageView avatar, twitter, github, location;
    TextView name, email;
    RelativeLayout topics, likes;
    ListView list;
    User user;
    List<Topic> recent;

    public ProfileFragment(String userName){
        this.userName = userName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile, null);
        findView();
        fetchData();
        return view;
    }

    private void fetchData(){
        ApiUtils.get(getUserUrl(), null , new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String responce) {
                user = new Gson().fromJson(responce, User.class);
                getActivity().setTitle(user.getName());
                try {
                    String topic = JsonUtils.getString(new JSONObject(responce), "topics");
                    recent = new Gson().fromJson(topic, new TypeToken<List<Topic>>() {
                    }.getType());
                    list.setAdapter(new FavoriteAdapter(recent, getActivity()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bindView();
            }

            @Override
            public void onFailure(Throwable error, String responce) {

            }
        });
    }

    private void bindView(){
        imageLoader.displayImage(user.getBigAvatar(300), avatar, options);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = new TextView(getActivity());
                tv.setText(user.getPop());
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(getResources().getColor(R.color.white));
                PopupWindow pop = new PopupWindow(tv, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
                pop.setBackgroundDrawable(new BitmapDrawable());
                pop.setOutsideTouchable(true);
                pop.setFocusable(true);
                pop.showAsDropDown(v);
            }

        });
        name.setText(user.getName());
        email.setText(user.getContactText());
        topics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new FavoriteFragment("", getUserTopics(), ((ProfileActivity) getActivity()).getPullToRefreshAttacher()))
                        .addToBackStack(null)
                        .commit();
            }
        });
        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new FavoriteFragment("", getUserLikes(), ((ProfileActivity)getActivity()).getPullToRefreshAttacher()))
                        .addToBackStack(null)
                        .commit();
            }
        });
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.github_url.length() > 0)startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(user.github_url)));
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.twitter.length() > 0)startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(user.getTwitter())));
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = new TextView(getActivity());
                tv.setText(user.location);
                tv.setTextColor(getResources().getColor(R.color.white));
                PopupWindow pop = new PopupWindow(tv, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
                pop.setBackgroundDrawable(new BitmapDrawable());
                pop.setOutsideTouchable(true);
                pop.setFocusable(true);
                pop.showAsDropDown(v, 0, -200);
            }
        });
    }

    private void findView(){
        avatar = (ImageView)view.findViewById(R.id.imageView);
        twitter = (ImageView)view.findViewById(R.id.iv_twitter);
        github = (ImageView)view.findViewById(R.id.iv_github);
        location = (ImageView)view.findViewById(R.id.iv_location);

        name = (TextView)view.findViewById(R.id.tv_name);
        email = (TextView)view.findViewById(R.id.tv_email);

        topics = (RelativeLayout)view.findViewById(R.id.rl_topics);
        likes = (RelativeLayout)view.findViewById(R.id.rl_likes);

        list = (ListView)view.findViewById(R.id.list);
    }

    private String getUserUrl(){
        return ApiUtils.USER_PROFILE + userName + ".json";
    }
    private String getUserTopics(){
        return ApiUtils.USER_PROFILE + userName + "/topics.json";
    }
    private String getUserLikes(){
        return ApiUtils.USER_PROFILE + userName + "/topics/favorite.json";
    }
}

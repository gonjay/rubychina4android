package org.rubychina.app.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.rubychina.app.R;
import org.rubychina.app.helper.MyBitmapDisplayer;
import org.rubychina.app.model.Node;
import org.rubychina.app.ui.LoginActivity;
import org.rubychina.app.ui.MainActivity;
import org.rubychina.app.ui.NotificationActivity;
import org.rubychina.app.ui.ProfileActivity;
import org.rubychina.app.ui.adapter.DrawerAdapter;
import org.rubychina.app.ui.fragment.topic.TopicsFragment;
import org.rubychina.app.utils.ApiUtils;
import org.rubychina.app.utils.UserUtils;

import java.util.ArrayList;

/**
 * Created by mac on 14-1-27.
 */
public class DrawerFragment extends Fragment {
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnFail(R.drawable.avatar).displayer(new MyBitmapDisplayer(100, 2000))
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
            .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

    private TextView login, email;
    private ListView mListView;
    private ImageView avatarView;
    private RelativeLayout bgRelativeLayout;

    private DrawerAdapter mAdapter;

    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.fragment_drawer, null);

        mainActivity = (MainActivity)getActivity();

        login = (TextView)contentView.findViewById(R.id.tv_login);
        email = (TextView)contentView.findViewById(R.id.tv_email);
        bgRelativeLayout = (RelativeLayout)contentView.findViewById(R.id.rl_dr_bg);

        login.setText(UserUtils.getUserLogin());
        email.setText(UserUtils.getUserEmail());
        bgRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!UserUtils.logined()) {
                    getActivity().startActivityForResult(new Intent(getActivity(), LoginActivity.class), MainActivity.ACTION_FOR_LOGIN);
                    getActivity().overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
                } else {
                    Intent i = new Intent(getActivity(), ProfileActivity.class);
                    i.putExtra("user", UserUtils.getUserLogin());
                    getActivity().startActivity(i);
                    getActivity().overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
                }
            }
        });

        avatarView = (ImageView)contentView.findViewById(R.id.iv_avatar);

        imageLoader.displayImage(UserUtils.getUserAvatar(),avatarView, options);

        mListView = (ListView) contentView.findViewById(R.id.listView);
        mAdapter = new DrawerAdapter(mListView);
        mListView.setAdapter(mAdapter);
        mListView.setItemChecked(0, true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListView.setItemChecked(position, true);
                callParent(position);
            }
        });
        return contentView;
    }

    private void callParent(int positiong){
        switch (positiong){
            case 0:
                mainActivity.setMainContent(new TopicsFragment(MainActivity.TOPICS, ApiUtils.TOPICS, ((MainActivity)getActivity()).getPullToRefreshAttacher()));
                break;
            case 1:
                mainActivity.setMainContent(new NodesFragment());
                break;
            case 2:
                if (UserUtils.logined()){
                    startActivity(new Intent(getActivity(), NotificationActivity.class));
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), MainActivity.ACTION_FOR_LOGIN);
                    getActivity().overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                }
                break;
            case 3:
                mainActivity.setMainContent(new TopUserFragment());
                break;
            case 4:
                getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ApiUtils.WIKI)));
                break;
            default:
                break;
        }
    }
}

package org.rubychina.app.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.rubychina.app.R;
import org.rubychina.app.helper.MyBitmapDisplayer;
import org.rubychina.app.ui.adapter.DrawerAdapter;

/**
 * Created by mac on 14-1-27.
 */
public class DrawerFragment extends Fragment {
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnFail(R.drawable.avatar).displayer(new MyBitmapDisplayer(100, 1000))
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
            .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();


    private ListView mListView;
    private ImageView avatarView;

    private DrawerAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.fragment_drawer, null);

        avatarView = (ImageView)contentView.findViewById(R.id.iv_avatar);

        imageLoader.displayImage("http://ruby-china.org/avatar/13986ecfd5a4465e209d85c64968cab8.png?s=120",avatarView, options);

        mListView = (ListView) contentView.findViewById(R.id.listView);
        mAdapter = new DrawerAdapter(mListView);
        mListView.setAdapter(mAdapter);
        mListView.setItemChecked(0, true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListView.setItemChecked(position, true);
            }
        });
        return contentView;

    }
}

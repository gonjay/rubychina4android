package org.rubychina.app.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.rubychina.app.R;

/**
 * Created by mac on 14-2-11.
 */
public class MyBaseAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context context;
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnFail(R.drawable.avatar).displayer(new RoundedBitmapDisplayer(100))
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
            .cacheInMemory(true)
            .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

    public MyBaseAdapter(Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}

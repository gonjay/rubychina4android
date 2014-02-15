package org.rubychina.app.helper;

import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;

/**
 * Created by mac on 14-2-15.
 */
public class ImageGetter implements Html.ImageGetter {
    @Override
    public Drawable getDrawable(String source) {
        Log.v("", source);
        return null;
    }
}

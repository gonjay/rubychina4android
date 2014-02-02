package org.rubychina.app.ui.fragment.Topic;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.rubychina.app.R;
import org.rubychina.app.model.Topic;
import org.rubychina.app.utils.ApiUtils;

/**
 * Created by mac on 14-2-2.
 */
public class TopicViewFragment extends Fragment {
    private TextView title, detail;

    private ImageView autherAvatar;

    private WebView webView;

    private Topic topic;

    public TopicViewFragment(Topic topic) {
        this.topic = topic;
    }

    View view;

    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnFail(R.drawable.avatar).displayer(new FadeInBitmapDisplayer(1000))
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
            .cacheInMemory(true)
            .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_topic, container, false);
        findView();
        updateView();
        return view;
    }

    private void updateView(){
        title.setText(topic.title);
        detail.setText(topic.getDetail());
        imageLoader.displayImage(topic.user.avatar_url, autherAvatar, options);
        webView.loadUrl("file:///android_asset/index.html");
        webView.addJavascriptInterface(new WebAppInterface(getActivity()), "isFromAndroid");
    }

    private void findView(){
        title = (TextView)view.findViewById(R.id.tv_title);
        detail = (TextView)view.findViewById(R.id.tv_detail);
        autherAvatar = (ImageView)view.findViewById(R.id.tv_avatar);
        webView = (WebView)view.findViewById(R.id.wv_content);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                webView.setVisibility(View.VISIBLE);
            }
        });
    }


    private class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public String getContentBody() {
            return topic.body;
        }

        @JavascriptInterface
        public String getContentBodyHtml() {
            return topic.body_html;
        }
    }
}

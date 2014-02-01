package org.rubychina.app.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
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
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.rubychina.app.R;
import org.rubychina.app.model.Topic;
import org.rubychina.app.utils.ApiParams;
import org.rubychina.app.utils.ApiUtils;


/**
 * Created by mac on 14-1-31.
 */
public class TopicActivity extends FragmentActivity {
    private TextView title, detail;

    private ImageView autherAvatar;

    private WebView webView;

    private Topic topic = new Topic();

    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnFail(R.drawable.avatar).displayer(new FadeInBitmapDisplayer(1000))
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
            .cacheInMemory(true)
            .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        findView();
        topic.id = getIntent().getStringExtra("topic_id");
        if (topic.id != null) fetchData();
    }

    private void fetchData(){
        ApiUtils.get(ApiUtils.TOPIC_VIEW + topic.id + ".json", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                topic = gson.fromJson(response, Topic.class);
                updateView();
            }
        });
    }

    private void updateView(){
        title.setText(topic.title);
        detail.setText(topic.getDetail());
        imageLoader.displayImage(topic.user.avatar_url, autherAvatar, options);
        webView.loadUrl("file:///android_asset/index.html");
        webView.addJavascriptInterface(new WebAppInterface(this), "isFromAndroid");
    }

    private void findView(){
        title = (TextView)findViewById(R.id.tv_title);
        detail = (TextView)findViewById(R.id.tv_detail);
        autherAvatar = (ImageView)findViewById(R.id.tv_avatar);
        webView = (WebView)findViewById(R.id.wv_content);
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
    }
}

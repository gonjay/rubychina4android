package org.rubychina.app.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.rubychina.app.R;

/**
 * Created by mac on 14-2-4.
 */
public class PreviewFragment extends Fragment {
    View v;
    WebView web;

    private String body;

    public PreviewFragment(String body){
        this.body = body;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_preview, container, false);
        web = (WebView) v.findViewById(R.id.wv_preview);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("file:///android_asset/preview.html");
        web.addJavascriptInterface(new WebAppInterface(getActivity()), "isFromAndroid");
        return v;
    }

    private class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public String getContentBody() {
            return body;
        }

        @JavascriptInterface
        public String getContentBodyHtml() {
            return "";
        }
    }
}

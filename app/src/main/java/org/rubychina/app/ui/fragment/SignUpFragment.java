package org.rubychina.app.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.rubychina.app.R;
import org.rubychina.app.utils.ApiUtils;

/**
 * Created by mac on 14-3-2.
 */
public class SignUpFragment extends Fragment{

    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        CookieSyncManager.createInstance(getActivity());
        CookieSyncManager.getInstance().startSync();
        CookieManager.getInstance().removeSessionCookie();

        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);

        webView = (WebView) v.findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("http://ruby-china.org/")){
                    Toast.makeText(getActivity(), R.string.sign_up_success, Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }
                return true;
            }

        });

        webView.loadUrl(ApiUtils.SIGN_UP);

        return v;
    }
}

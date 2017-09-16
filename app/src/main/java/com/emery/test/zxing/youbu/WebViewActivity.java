package com.emery.test.zxing.youbu;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.emery.test.zxing.R;

/**
 * 只有一个webview的页面
 *
 * @author zhouyeliang
 */
public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BetterFabricsWebFragment";

    private String title;

    private String url;

    protected WebView webView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initUI();
    }

    public void initUI() {
        webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.cancel(); // Android默认的处理方式
                handler.proceed(); // 接受所有网站的证书
                //handleMessage(Message msg); // 进行其他处理
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url.equals("https://epayplatform.frontpay.cn/Content/Files/%E8%B4%A6%E6%88%B7%E8%B5%84%E9%87%91%E5%91%A8%E6%9C%9F%E8%AF%B4%E6%98%8E.pdf")) {
//                } else {
//                    view.loadUrl(url);
//                }
//                return true;
                                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("gb2312");
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBuiltInZoomControls(true);//support zoom
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setDownloadListener(new MyWebViewDownLoadListener(this));

        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);

        //点击返回
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            WebViewActivity.this.finish();
        }
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * webView下载类
     */
    public class MyWebViewDownLoadListener implements DownloadListener {

        private Context mContext;

        public MyWebViewDownLoadListener(Context mContext){
            this.mContext=mContext;
        }
        @Override
        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype, long contentLength) {

            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mContext.startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}

package org.workholick.doc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import org.workholick.doc.service.DocumentsWebViewImpl;

public class DocActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc);
        WebView webView=findViewById(R.id.webView);
        webView.addJavascriptInterface(new DocumentsWebViewImpl(this),"docView");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/html/index.html");
    }
}

package com.example.webviewtest.app;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    private static final int MY_NOTIFICATION_ID=1;
    NotificationManager notificationManager;
    Notification myNotification;
    static WebView webView;
    static Activity activityContex;

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.w("ANDREW", "Alarm fired.");

        if (webView == null) {
            Log.w("ANDREW", "Creating webview");
            final Context ctx = context.getApplicationContext();//activityContex != null ? activityContex : context;
            if (activityContex != null) {
                Log.w("ANDREW", "With activity context");
            }
            webView = new WebView(ctx);
            //getWindow().requestFeature(Window.FEATURE_PROGRESS);

            webView.getSettings().setJavaScriptEnabled(true);

            webView.setWebViewClient(new WebViewClient() {
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(ctx, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                }

            });
            webView.setWebChromeClient(new WebChromeClient() {
                public boolean onConsoleMessage(android.webkit.ConsoleMessage consoleMessage) {
                    Log.w("CONSOLE", consoleMessage.message());
                    if (activityContex == null) {
                        Log.w("ANDREW", "Starting activity from receiver");
                        Intent i = new Intent(context, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                    return true;
                }
            });

            webView.loadUrl("http://jsbin.com/sulozihe/4");
            if (activityContex != null) {
              //  ((FrameLayout)activityContex.findViewById(R.id.container)).addView(webView);
            }

        } else {
            Log.w("ANDREW", "Executing WebView JS");
            webView.loadUrl("javascript:cats()");
        }
    }
}

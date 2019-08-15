package com.anysoftkeyboard.ui.settings.setup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.menny.android.anysoftkeyboard.LauncherSettingsActivity;
import com.menny.android.anysoftkeyboard.R;

public class ConsentPage extends AppCompatActivity {
    WebView webView;
    SharedPreferences spref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: Solve the auto login bug
//        spref = getSharedPreferences("login",MODE_PRIVATE);
//        Boolean logined = spref.getBoolean("logined", false);
//        if (logined) {
//            String message = "You've successfully logged in";
//            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
//            Intent toLogging = new Intent(ConsentPage.this, LoggingPage.class);
//            startActivity(toLogging);
//            Log.d("test", "logined in");
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent_page);
        // init webView
        webView = (WebView) findViewById(R.id.consentWebView);
        // displaying content in WebView from html file that stored in assets folder
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/" + "consent_full.html");

        final Button agree_button = (Button) findViewById(R.id.btn_agree);
        agree_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsentPage.this, LoggingPage.class);
                startActivity(intent);
            }
        });

        final Button exit_button = (Button) findViewById(R.id.btn_exit);
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });

        final Button disagree_button = (Button) findViewById(R.id.btn_disagree);
        disagree_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });
    }
}

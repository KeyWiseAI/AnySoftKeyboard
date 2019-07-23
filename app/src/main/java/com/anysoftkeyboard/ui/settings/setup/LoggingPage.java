package com.anysoftkeyboard.ui.settings.setup;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.menny.android.anysoftkeyboard.R;

public class LoggingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logging_page);
//
//        final Button button = findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
//                editor.putBoolean(STARTED_PREF_KEY, true);
//                SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
//                refreshWizardPager();
//            }
//        });
    }
}

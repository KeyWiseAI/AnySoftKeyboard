package com.anysoftkeyboard.ui.settings.setup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import com.menny.android.anysoftkeyboard.LauncherSettingsActivity;
import com.menny.android.anysoftkeyboard.R;

import java.util.regex.Pattern;

public class LoggingPage extends AppCompatActivity {
    SharedPreferences spref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        spref = getSharedPreferences("login",MODE_PRIVATE);
        Boolean logined = spref.getBoolean("logined", false);
        if (logined) {
            String message = "You've successfully logged in";
            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent toLogging = new Intent(LoggingPage.this, LauncherSettingsActivity.class);
            startActivity(toLogging);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging_page);

        EditText email_edText = (EditText) findViewById(R.id.input_email);
        EditText password_edText = (EditText) findViewById(R.id.input_password);




        final Button button = (Button) findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  input_email = email_edText.getText().toString();
                String  input_password = password_edText.getText().toString();
                if(input_email.matches("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Email should not be empty", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, -130);
                    toast.show();
                    return;
                }

                if(!isValidEmail(input_email)){
                    Toast toast = Toast.makeText(getApplicationContext(), "Not a valid email address", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, -130);
                    toast.show();
                    return;
                }

                if(input_password.matches("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Password should not be empty", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, -130);
                    toast.show();
                    return;
                }

                SharedPreferences.Editor editor =  spref.edit();
                editor.putBoolean("logined", true);
                editor.commit();
                Intent toLogging = new Intent(LoggingPage.this, LauncherSettingsActivity.class);
                startActivity(toLogging);
            }
        });

        final Button exit_button = (Button) findViewById(R.id.btn_exit);
        exit_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });

    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}

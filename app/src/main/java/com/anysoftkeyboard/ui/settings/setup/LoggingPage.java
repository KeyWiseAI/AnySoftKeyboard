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

import static android.util.Patterns.EMAIL_ADDRESS;

public class LoggingPage extends AppCompatActivity {
    SharedPreferences spref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // used the boolean 'login' to check if the user have logged or not.
        spref = getSharedPreferences("login",MODE_PRIVATE);
        // the default value is false
        Boolean logined = spref.getBoolean("logined", false);
        //if the user have logged, toss the logining message and jump to the next page directly
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
                //get the inputs of email and password
                String  input_email = email_edText.getText().toString();
                String  input_password = password_edText.getText().toString();
                // reject the empty email
                if(input_email.matches("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Email should not be empty", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, -130);
                    toast.show();
                    return;
                }

                //check the email is valid
                if(!isValidEmail(input_email)){
                    Toast toast = Toast.makeText(getApplicationContext(), "Not a valid email address", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, -130);
                    toast.show();
                    return;
                }
                // reject the empty password
                if(input_password.matches("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Password should not be empty", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, -130);
                    toast.show();
                    return;
                }

                //if both the email and password are valid, change "login" to false
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


    // for email validation
    private boolean isValidEmail(String email) {
        Pattern pattern = EMAIL_ADDRESS_PATTERN;
        return pattern.matcher(email).matches();
    }

    //the valid email pattern, which refers to
    //https://en.wikipedia.org/wiki/Email_address#RFC_specification
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "(?:[a-z0-9A-Z!#$%&'*+/=?^_`{|}~-]{1,64}+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
   );
}

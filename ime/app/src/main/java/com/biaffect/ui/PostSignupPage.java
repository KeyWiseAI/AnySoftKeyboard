package com.biaffect.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.biaffect.bridge.BiAffectBridge;
import com.menny.android.anysoftkeyboard.LauncherSettingsActivity;
import com.menny.android.anysoftkeyboard.R;

import org.sagebionetworks.bridge.rest.exceptions.ConsentRequiredException;
import org.sagebionetworks.bridge.rest.model.UserSessionInfo;

import java.util.regex.Pattern;

import rx.Subscription;

public class PostSignupPage extends AppCompatActivity {

    private String _email;
    private String _password;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        _email = getIntent().getStringExtra( "username" );
        _password = getIntent().getStringExtra( "password" );

        setContentView( R.layout.activity_post_signup_page );

        ((TextView)findViewById( R.id.verify_email )).setText( getString( R.string.check_email,
                                                                          _email ) );

        logInSilently();

        findViewById( R.id.btn_next ).setOnClickListener( __ -> signIn() );

        findViewById( R.id.btn_send_email ).setOnClickListener( __ -> {
            String inputEmail = ((TextView)findViewById( R.id.input_email )).getText().toString();

            if( TextUtils.isEmpty( inputEmail ) ) {
                Toast toast = Toast.makeText( getApplicationContext(),
                                              "Email should not be empty",
                                              Toast.LENGTH_SHORT );
                toast.setGravity( Gravity.CENTER, 0, -130 );
                toast.show();
                return;
            }

            if( !EMAIL_ADDRESS_PATTERN.matcher( inputEmail ).matches() ) {
                Toast toast = Toast.makeText( getApplicationContext(),
                                              "Not a valid email address",
                                              Toast.LENGTH_SHORT );
                toast.setGravity( Gravity.CENTER, 0, -130 );
                toast.show();
                return;
            }

            _email = inputEmail;

            Subscription s = BiAffectBridge.resendEmail( _email )
                                           .subscribe( this::onResendComplete, this::onSignInError );
        } );
    }

    private void logInSilently() {
        if( null == _email || null == _password ) {
            return;
        }
        Subscription s = BiAffectBridge.logIn( _email, _password )
                                       .subscribe(
                                               this::onSignInSuccess,
                                               (ignore) -> {}
                                       );
    }

    private void signIn() {
        if( null == _email || null == _password ) {
            Toast toast = Toast.makeText( getApplicationContext(),
                                          "Error, restart the app and try again",
                                          Toast.LENGTH_SHORT );
            toast.setGravity( Gravity.CENTER, 0, -130 );
            toast.show();
            return;
        }
        Subscription s = BiAffectBridge.logIn( _email, _password )
                                       .subscribe( this::onSignInSuccess, this::onSignInError );
    }

    private void onResendComplete() {
        runOnUiThread( () -> {
            Toast toast = Toast.makeText( getApplicationContext(),
                                          "Resend email success",
                                          Toast.LENGTH_SHORT );
            toast.setGravity( Gravity.CENTER, 0, -130 );
            toast.show();
            ( (TextView)findViewById( R.id.verify_email ) ).setText( getString( R.string.check_email,
                                                                                _email ) );
        } );
    }

    private void onSignInSuccess( UserSessionInfo ignore ) {
        startActivity( new Intent( this, LauncherSettingsActivity.class ) );
    }

    private void onSignInError( Throwable throwable ) {
        if( throwable instanceof ConsentRequiredException ) {
            UserSessionInfo session = ( (ConsentRequiredException)throwable ).getSession();
            Subscription s = BiAffectBridge
                                     .bypassConsent( session )
                                     .subscribe( ( __ ) -> signIn(),
                                                 ( error ) -> onSignInError( new Throwable( "consent" ) ) );
            return;
        }
        // get the error message from backend
        String                 errorMsg = throwable.getMessage();
        ErrorMsgDialogFragment dialog   = new ErrorMsgDialogFragment();
        // handle the error message
        switch( errorMsg ) {
            case "Account not found.":
                errorMsg = "Incorrect/Unregistered email or incorrect password";
                break;
            case "Unable to resolve host \"webservices.sagebridge.org\": No address associated with hostname":
                errorMsg = "Internet Error, please check your internet connection";
                break;
            case "consent":
                errorMsg = "Consent error";
                break;
            default:
                //do nothing to replace the error message
                break;
        }
        // use setArguments to pass the error message to the dialog
        Bundle args = new Bundle();
        args.putString( "msgName", errorMsg );
        dialog.setArguments( args );
        // show the dialog to the user
        dialog.show( getSupportFragmentManager(), "errorMsg" );
//                            Log.d("login error", errorMsg);
    }

    //the valid email pattern, which refers to
    //https://en.wikipedia.org/wiki/Email_address#RFC_specification
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "(?:[a-z0-9A-Z!#$%&'*+/=?^_`{|}~-]{1,64}+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
    );

    // use DialogFragment class to show the error message
    public static class ErrorMsgDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog( Bundle savedInstanceState ) {
            AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
            // get the content of error message
            builder.setMessage( getArguments().getString( "msgName" ) )
                   .setNegativeButton( "Confirm", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick( DialogInterface dialog, int which ) {
                           ErrorMsgDialogFragment.this.getDialog().cancel();
                       }
                   } );
            return builder.create();
        }
    }
}

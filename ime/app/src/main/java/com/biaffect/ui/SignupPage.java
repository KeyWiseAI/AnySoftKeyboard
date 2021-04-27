package com.biaffect.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.anysoftkeyboard.ui.settings.MainSettingsActivity;
import com.biaffect.bridge.BiAffectBridge;
import com.menny.android.anysoftkeyboard.LauncherSettingsActivity;
import com.menny.android.anysoftkeyboard.R;

import org.sagebionetworks.bridge.rest.exceptions.ConsentRequiredException;
import org.sagebionetworks.bridge.rest.model.SharingScope;
import org.sagebionetworks.bridge.rest.model.SignUp;
import org.sagebionetworks.bridge.rest.model.UserSessionInfo;

import java.util.regex.Pattern;

import rx.Subscription;

public class SignupPage extends AppCompatActivity {
    private SignUp _signUp;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_signup_page );

        findViewById( R.id.btn_next ).setOnClickListener( __ -> {
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

            String inputPassword = ((TextView)findViewById( R.id.input_password )).getText().toString();
            if( TextUtils.isEmpty( inputPassword ) ) {
                Toast toast = Toast.makeText( getApplicationContext(),
                                              "Password should not be empty",
                                              Toast.LENGTH_SHORT );
                toast.setGravity( Gravity.CENTER, 0, -130 );
                toast.show();
                return;
            }

            String inputConfirm = ((TextView)findViewById( R.id.input_confirm )).getText().toString();
            if( TextUtils.isEmpty( inputConfirm ) ) {
                Toast toast = Toast.makeText( getApplicationContext(),
                                              "Confirm Password should not be empty",
                                              Toast.LENGTH_SHORT );
                toast.setGravity( Gravity.CENTER, 0, -130 );
                toast.show();
                return;
            }

            if( !TextUtils.equals( inputPassword, inputConfirm ) ) {
                Toast toast = Toast.makeText( getApplicationContext(),
                                              "Confirm Password does not match",
                                              Toast.LENGTH_SHORT );
                toast.setGravity( Gravity.CENTER, 0, -130 );
                toast.show();
                return;
            }

            boolean shareAll = ((RadioButton)findViewById( R.id.share_all )).isChecked();
            boolean shareRestrict = ((RadioButton)findViewById( R.id.share_biaffect )).isChecked();
            if( !shareAll && !shareRestrict ) {
                Toast toast = Toast.makeText( getApplicationContext(),
                                              "You much choose a sharing scope",
                                              Toast.LENGTH_SHORT );
                toast.setGravity( Gravity.CENTER, 0, -130 );
                toast.show();
                return;
            }

            SharingScope scope = shareAll ?
                                 SharingScope.ALL_QUALIFIED_RESEARCHERS :
                                 SharingScope.SPONSORS_AND_PARTNERS;

            _signUp = new SignUp().email( inputEmail )
                                  .password( inputPassword )
                                  .firstName( ((TextView)findViewById( R.id.input_first )).getText().toString() )
                                  .lastName( ((TextView)findViewById( R.id.input_last )).getText().toString() )
                                  .sharingScope( scope );

            //This should really be changed to a better implementation, but it's not worth it because we could just
            //be using lifecycle things later
            //also, we shouldn't be exposing the SignUp object outside of the BiAffectBridge class
            Subscription s = BiAffectBridge.signUp( _signUp )
                                           .subscribe( this::onSignUpComplete, this::onSignInError );
        } );

    }

    private void onSignUpComplete() {
        Intent intent = new Intent( this, PostSignupPage.class );
        intent.putExtra( "username", _signUp.getEmail() );
        intent.putExtra( "password", _signUp.getPassword() );
        startActivity( intent );
    }

    private void onSignInError( Throwable throwable ) {
        //TODO I don't think we get a consent required exception here, so
//        if( throwable instanceof ConsentRequiredException ) {
//            UserSessionInfo session = ( (ConsentRequiredException)throwable ).getSession();
//            Subscription s = BiAffectBridge
//                                     .bypassConsent( session )
//                                     .subscribe( ( __ ) -> signIn( _email, _password ),
//                                                 ( error ) -> onSignInError( new Throwable( "consent" ) ) );
//            return;
//        }
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

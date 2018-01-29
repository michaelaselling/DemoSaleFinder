package com.example.micka.demosalefinder;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.List;

/*
* Handles member log in.
*/

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private static Context context;
    private EditText email, password;
    private TextView emailWarning, passwordWarning;
    private MemberStorage memberStorage;
    private String memberEmail, memberPassword;

    /**
     * Initializes activity. Gets contexts from
     * interface method. Creates an instance
     * of interface MemberStorage by calling
     * singleton method. Gets all members from database
     * and saves it in Session-object. Calls on methods
     * to initialize views and set warnings invisible.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();
        memberStorage = DBMemberStorage.getInstance();
        Session.getInstance().setStoredMembers(memberStorage.getAllMembers());
        initViews();
        setEmailWarningInvisible();
        setPasswordWarningInvisible();
    }

    /**
     * Initializes views from XML file.
     */
    private void initViews() {
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        emailWarning = (TextView) findViewById(R.id.warningEmail);
        passwordWarning = (TextView) findViewById(R.id.warningPassword);
    }

    /**
     * Static method which returns context for MainActivity.
     * @return
     */
    public static Context getAppContext() { return MainActivity.context; }

    /**
     * Listener to log in button. Sets warnings
     * invisible and calls method to check if
     * email and password is correct. If  false,
     * warning message will appear in GUI. If true,
     * warnings sets invisible, member stores in
     * Session-object and MapActivity starts.
     * @param view
     */
    public void onLogInClick(View view) {
        Log.d(LOG_TAG, "onLogInClick()");

        if (view.getId() == R.id.loginButton) {

            setEmailWarningInvisible();
            setPasswordWarningInvisible();

            boolean emailExist = checkIfEmailExists();
            boolean passwordExist = checkIfPasswordExists();

            if (!emailExist) {
                setEmailWarningVisible();

            } if (!passwordExist) {
                setPasswordWarningVisible();

            } else if (emailExist && passwordExist){
                setEmailWarningInvisible();
                setPasswordWarningInvisible();
                Session.getInstance().setCurrentMember(memberEmail, memberPassword);
                Intent i = new Intent(MainActivity.this, MapActivity.class);
                startActivity(i);
            }
        }
    }

    /**
     * Listener to create member button and
     * starts MemberActivity.
     * @param view
     */
    public void onCreateMemberClick(View view) {
        Log.d(LOG_TAG, "onCreateMemberClick");

       if (view.getId() == R.id.createMember) {
           Intent i = new Intent(MainActivity.this, MemberActivity.class);
           startActivity(i);
        }
    }

    /*
     * Calls method from Session class to check
     * if email exists.
     */
    private Boolean checkIfEmailExists() {
        Log.d(LOG_TAG, "checkIfEmailExists()");

       memberEmail = email.getText().toString();
       boolean emailExist = Session.getInstance().checkIfEmailExists(memberEmail);

       if(emailExist) {
           return true;
       } else {
           return false;
       }
    }

    /*
     * Calls method from Session class to check
     * if password exists.
     */
    private Boolean checkIfPasswordExists() {
        Log.d(LOG_TAG, "checkIfPasswordExists()");

        memberPassword = password.getText().toString();
        boolean passwordExist = Session.getInstance().checkIfPasswordExists(memberPassword);

        if(passwordExist) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Sets warning for incorrect email visible.
     */
    private void setEmailWarningVisible() { emailWarning.setVisibility(View.VISIBLE); }

    /*
     * Sets warning for incorrect email invisible.
     */
    private void setEmailWarningInvisible() { emailWarning.setVisibility(View.INVISIBLE); }

    /*
     * Sets warning for incorrect password visible.
     */
    private void setPasswordWarningVisible() { passwordWarning.setVisibility(View.VISIBLE); }

    /*
     * Sets warning for incorrect password invisible.
     */
    private void setPasswordWarningInvisible() { passwordWarning.setVisibility(View.INVISIBLE); }
}

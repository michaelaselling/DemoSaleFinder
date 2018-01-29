package com.example.micka.demosalefinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/*
* Creates a new member
 */

public class MemberActivity extends AppCompatActivity {

    private static final String LOG_TAG = MemberActivity.class.getName();

    private EditText firstName, lastName, email, password = null;
    private TextView warningFirstName, warningLastName, warningEmail, warningPassword = null;
    private String memberFirstName, memberLastName, memberEmail, memberPassword;
    private MemberStorage memberStorage;

    /**
     * Initializes activity. Creates an instance
     * of interface MemberStorage by calling singleton
     * method. Calls method to initialize views and to
     * set all warnings invisible.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        memberStorage = DBMemberStorage.getInstance();
        initViews();
        setFirstNameWarningInvisible();
        setLastNameWarningInvisible();
        setEmailWarningInvisible();
        setPasswordWarningInvisible();
    }

    /*
     * Initializes views from activity_member(XML)
     */
    private void initViews() {
        firstName = (EditText) findViewById(R.id.createFirstName);
        lastName = (EditText) findViewById(R.id.createLastName);
        email = (EditText) findViewById(R.id.createEmail);
        password = (EditText) findViewById(R.id.createPassword);
        warningFirstName = (TextView) findViewById(R.id.warningFirstName);
        warningLastName = (TextView) findViewById(R.id.warningLastName);
        warningEmail = (TextView) findViewById(R.id.warningEmail);
        warningPassword = (TextView) findViewById(R.id.warningPassword);
    }

    /**
     * Listener on back button in XML and goes
     * back to previous activity in GUI.
     * @param view
     */
    public void backAccountOnClick(View view) {
        Log.d(LOG_TAG, "Back button has been clicked" );

        if (view.getId() == R.id.backButton) {
            Intent i = new Intent(MemberActivity.this, MainActivity.class);
            startActivity(i);
        }
    }

    /**
     * Is called whenever this activity starts.
     * Sets all warnings invisible and listens to
     * create member button. If pressed, it will
     * call method to check for invalid input.
     * @param view
     */
    public void createAccountOnClick(View view) {
        Log.d(LOG_TAG, "createAccoutOnClick()");
        setFirstNameWarningInvisible();
        setLastNameWarningInvisible();
        setEmailWarningInvisible();
        setPasswordWarningInvisible();
        if (view.getId() == R.id.createMemberButton) {
            checkWarnings();
        }
    }

    /*
     * Checks field input for firstname, lastname,
     * email and password and if false, it will
     * call methods to set warning in GUI. Else,
     * it will create member with given input, calls
     * interface method to store member and saves
     * member in Session-object.
     */
    private void checkWarnings() {
        Log.d(LOG_TAG, "checkWarnings()");

        boolean firstName = checkFirstName();
        boolean lastName = checkLastName();
        boolean email = checkEmail();
        boolean password = checkPassword();

        if(!firstName) {
            setFirstNameWarningVisible();

        } if (!lastName) {
            setLastNameWarningVisible();

        } if (!email) {
            setEmailWarningVisible();

        } if (!password) {
            setPasswordWarningVisible();

        } else if (firstName && lastName && email && password) {

            Member member = createMember();
            Session.getInstance().setMember(memberStorage.storeMember(member));

            Intent i = new Intent(MemberActivity.this, MainActivity.class);
            startActivity(i);
        }
    }

    //Only used by Unit test.
    protected void setFirstName(EditText firstName) {

        this.firstName=firstName;
    }

    /*
     * Checks if textview for firstname is empty and
     * returns boolean of field input for firstname.
     * No public access, only accessable in same package.
     * Used from unit tests.
     */
     boolean checkFirstName() {
       // Log.d(LOG_TAG, "checkFirstName()");

        boolean checkFirstName = true;
        if (firstName.getText().toString().length() == 0) {
            checkFirstName = false;
        }
        return checkFirstName;
    }

    /*
     * Checks if textview for lastname is empty and
     * returns boolean of field input for lastname.
     */
    private boolean checkLastName() {
        Log.d(LOG_TAG, "checkLastName()");

        boolean checkLastName = true;
        if (lastName.getText().toString().length() == 0) {
            checkLastName = false;
        }
        return checkLastName;
    }

    /*
     * Checks if textview for email is empty and
     * returns boolean of field input for email.
     */
    private boolean checkEmail() {
        Log.d(LOG_TAG, "checkEmail()");

        boolean checkEmail = true;
        if (email.getText().toString().length() == 0) {
            checkEmail = false;
        }
        return checkEmail;
    }

    /*
     * Checks if textview for password is empty and
     * returns boolean of field input for password.
     */
    private boolean checkPassword() {
        Log.d(LOG_TAG, "checkPassword()");

        boolean checkPassword = true;
        if (password.getText().toString().length() == 0) {
            checkPassword = false;
        }
        return checkPassword;
    }

    /*
     * Sets warning for no input on firstname visible.
     */
    private void setFirstNameWarningVisible() { warningFirstName.setVisibility(View.VISIBLE);}

    /*
     * Sets warning for no input on firstname invisible.
     */
    private void setFirstNameWarningInvisible() {
        warningFirstName.setVisibility(View.INVISIBLE);
    }

    /*
     * Sets warning for no input on lastname visible.
     */
    private void setLastNameWarningVisible() {
        warningLastName.setVisibility(View.VISIBLE);
    }

    /*
     * Sets warning for no input on lastname invisible.
     */
    private void setLastNameWarningInvisible() {
        warningLastName.setVisibility(View.INVISIBLE);
    }

    /*
     * Sets warning for no input on email visible.
     */
    private void setEmailWarningVisible() {
        warningEmail.setVisibility(View.VISIBLE);
    }

    /*
     * Sets warning for no input on email invisible.
     */
    private void setEmailWarningInvisible() {
        warningEmail.setVisibility(View.INVISIBLE);
    }

    /*
     * Sets warning for no input on password visible.
     */
    private void setPasswordWarningVisible() {
        warningPassword.setVisibility(View.VISIBLE);
    }

    /*
     * Sets warning for no input on password invisible.
     */
    private void setPasswordWarningInvisible() {
        warningPassword.setVisibility(View.INVISIBLE);
    }

    /*
     * Creates and returns member.
     */
    private Member createMember() {
        Log.d(LOG_TAG, "createMember()");

        memberFirstName = firstName.getText().toString();
        memberLastName = lastName.getText().toString();
        memberEmail = email.getText().toString();
        memberPassword = password.getText().toString();

        Member member = new Member(memberFirstName, memberLastName, memberEmail, memberPassword);
        System.out.println(member.toString());
        return member;
    }

}

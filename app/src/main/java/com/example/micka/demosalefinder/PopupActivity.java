package com.example.micka.demosalefinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Handels popup window for delete favorite shop.
 */

public class PopupActivity extends AppCompatActivity {

    private static final String LOG_TAG = AccountActivity.class.getSimpleName();

    /**
     * Initializes activity and adds name of
     * shop that wants to be deleted, in window.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        setFavoriteShopInTitle();
    }
    /**
     * Sets warning message in GUI.
     */
    public void setFavoriteShopInTitle() {
        Log.d(LOG_TAG, "setFavoriteShopInTitle()");

        TextView textView = (TextView) findViewById(R.id.title);
        textView.setText("Vill du ta bort " + Session.getInstance().getShop().getName() + "?");
    }

    /**
     * Goes back to previous activity in GUI.
     */
    public void onExitClick(View view) {
        Log.d(LOG_TAG, "onExitClick()");

        if(view.getId() == R.id.exit) {
            AccountActivity.checkStatusForDeleteFavoriteShop(false);
            Intent i = new Intent(PopupActivity.this, AccountActivity.class);
            startActivity(i);
        }
    }

    /**
     * Calls on static method
     * checkStatusForDeleteFavoriteShop and sets
     * status to true. It then starts previous
     * activity in GUI.
     */
    public void onDeleteClick(View view) {
        Log.d(LOG_TAG, "onDeleteClick()");

        if(view.getId() == R.id.delete) {
            AccountActivity.checkStatusForDeleteFavoriteShop(true);
            Intent i = new Intent(PopupActivity.this, AccountActivity.class);
            startActivity(i);
        }
    }
}

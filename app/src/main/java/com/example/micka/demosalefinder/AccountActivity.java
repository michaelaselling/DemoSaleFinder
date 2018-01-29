package com.example.micka.demosalefinder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Shows favorite stores for member.
 */

public class AccountActivity extends AppCompatActivity {

    private static final String LOG_TAG = AccountActivity.class.getSimpleName();
    private static boolean deleteShopStatus;
    private Shop shop;
    private MemberStorage memberStorage;

    //arraylist of all textviews with favorit shop title
    private int[] allTextViews = new int[] {
            R.id.shop1,
            R.id.shop2,
            R.id.shop3,
            R.id.shop4,
            R.id.shop5,
            R.id.shop6,
            R.id.shop7,
            R.id.shop8,
            R.id.shop9,
            R.id.shop10,
            R.id.shop11
    };

    //arraylist of all delete favorit shop buttons
    private int[] allDeleteButtons = new int[] {
            R.id.deleteShop1,
            R.id.deleteShop2,
            R.id.deleteShop3,
            R.id.deleteShop4,
            R.id.deleteShop5,
            R.id.deleteShop6,
            R.id.deleteShop7,
            R.id.deleteShop8,
            R.id.deleteShop9,
            R.id.deleteShop10,
            R.id.deleteShop11
    };

    //arraylist of all website buttons for favorite shops
    private int[] allWebsiteButtons = new int[] {
            R.id.websiteShop1,
            R.id.websiteShop2,
            R.id.websiteShop3,
            R.id.websiteShop4,
            R.id.websiteShop5,
            R.id.websiteShop6,
            R.id.websiteShop7,
            R.id.websiteShop8,
            R.id.websiteShop9,
            R.id.websiteShop10,
            R.id.websiteShop11
    };

    /**
     * Initializes activity. Creates an instance
     * of interface MemberStorage by calling singleton
     * method. Gets all favorite shops and saves it in
     * Session-object. Shows members firstname and lastname.
     * Sets buttons and titles invisible in GUI and always
     * checks if a favorite shop has been selected to be deleted.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        memberStorage = DBMemberStorage.getInstance();
        Session.getInstance().getMember().setFavoriteShops(memberStorage.getAllFavoriteShops());
        showMember();
        setAllDeleteButtonsInvisible();
        setAllWebsiteButtonsInvisible();
        resetAllFavoriteShopTitles();
        showAllFavoriteShops();
        deleteFavoriteShop();
    }

    /*
     * Hides all delete buttons for favorite shops in GUI.
     */
    private void setAllDeleteButtonsInvisible() {
        Log.d(LOG_TAG, "setAllDeleteButtonsInvisible()");

        for(int i = 0; i < allDeleteButtons.length; i++) {
            Button button  = (Button) findViewById(allDeleteButtons[i]);
            button.setVisibility(View.INVISIBLE);
        }
    }

    /*
     * Hides all website buttons for favorite shops in GUI.
     */
    private void setAllWebsiteButtonsInvisible() {
        Log.d(LOG_TAG, "setAllWebsiteButtonsInvisible()");

        for(int i = 0; i < allWebsiteButtons.length; i++) {
            Button button  = (Button) findViewById(allWebsiteButtons[i]);
            button.setVisibility(View.INVISIBLE);
        }
    }

    /*
     * Hides all titles for favorite shops in GUI.
     */
    private void resetAllFavoriteShopTitles() {
        Log.d(LOG_TAG, "resetAllFavoriteShopTitles()");

        for(int i = 0; i < allTextViews.length; i++) {
            TextView textView = (TextView) findViewById(allTextViews[i]);;
            textView.setText("");
        }
    }

    /*
     * Shows firstname and lastname in GUI of current member.
     */
    private void showMember() {
        Log.d(LOG_TAG, "showMember()");

        TextView memberName = (TextView) findViewById(R.id.memberName);
        memberName.setText(Session.getInstance().getMember().getFirstName() + " " +
                            Session.getInstance().getMember().getLastName());
        memberName.setTextSize(20);
    }

    /*
     * Starts website for favorite shop in GUI.
     */
    private void startWebsiteForFavoriteShop(int index) {
        Log.d(LOG_TAG, "startWebsiteForFavoriteShop()");

        String website = Session.getInstance().getMember().getFavoriteShops().get(index).getWebsite();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
        startActivity(browserIntent);
    }

    /*
     * Gets favorite shop from setShop-method and
     * starts PopupActivity to make user confirm or
     * cancel that favorite shop should be deleted.
     */
    private void startPopUpActivity(int index) {
        Log.d(LOG_TAG, "startPopUpActivity()");

        shop = setShop(index);
        Session.getInstance().setShop(shop);
        Intent i = new Intent(AccountActivity.this, PopupActivity.class);
        startActivity(i);
    }

    /*
    * Saves favorite shop that will be deleted,
    * in Session-object and returns that shop.
    */
    private Shop setShop(int index) {
        Log.d(LOG_TAG, "setShop()");

        int id = Session.getInstance().getMember().getFavoriteShops().get(index).getId();
        String name = Session.getInstance().getMember().getFavoriteShops().get(index).getName();
        String address = Session.getInstance().getMember().getFavoriteShops().get(index).getAddress();
        int salePercentage = Session.getInstance().getMember().getFavoriteShops().get(index).getSalePercentage();
        ShopType shoptype = Session.getInstance().getMember().getFavoriteShops().get(index).getShopType();
        double latitude = Session.getInstance().getMember().getFavoriteShops().get(index).getLatitude();
        double longitude = Session.getInstance().getMember().getFavoriteShops().get(index).getLongitude();
        String website = Session.getInstance().getMember().getFavoriteShops().get(index).getWebsite();

        shop = new Shop(id, name, address, salePercentage, shoptype, latitude, longitude, website);
        Session.getInstance().setShop(shop);
        return shop;
    }

    /**
     * Is called from PopupActivity and gets
     * boolean as parameter to se if favorite shop
     * should be deleted or not. Value is stored
     * and used in deleteFavoriteShop().
     * @param deleteShop
     */
    public static void checkStatusForDeleteFavoriteShop(Boolean deleteShop) {
        Log.d(LOG_TAG, "checkStatusForDeleteFavoriteShop()");

        if(deleteShop) {
            deleteShopStatus = true;
        } else {
            deleteShopStatus = false;
        }
    }

    /**
     * Checks if favorite shop should be deleted.
     * If true, it will call interface and delete
     * shop in database. If interface-method returns
     * true, it will call on methods to delete favorite
     * shop from GUI and show all favorite shops.
     */
    public void deleteFavoriteShop() {
        Log.d(LOG_TAG, "deleteFavoriteShop()");

        if(deleteShopStatus) {
            boolean deleteStatus = memberStorage.deleteFavoriteShop(Session.getInstance().getShop());

            if(deleteStatus) {
                setAllDeleteButtonsInvisible();
                setAllWebsiteButtonsInvisible();
                resetAllFavoriteShopTitles();
                showAllFavoriteShops();
            }
        }
    }

    /**
     * Listener on back button in XML and goes
     * back to previous activity in GUI.
     * @param view
     */
    public void onBackClick(View view) {
        Log.d(LOG_TAG, "onBackClick()");

        if (view.getId() == R.id.backButton) {
            Intent i = new Intent(AccountActivity.this, MapActivity.class);
            startActivity(i);
        }
    }

    /**
     * Goes back to Main activity in GUI.
     * @param view
     */
    public void onLogOutClick(View view) {
        Log.d(LOG_TAG, "onLogOutClick()");

        if (view.getId() == R.id.logOut) {
            Intent i = new Intent(AccountActivity.this, MainActivity.class);
            startActivity(i);
        }
    }

    /**
     * Shows all favorite shops in GUI by Session-instance
     * of all saved favorite shops from database.
     */
    public void showAllFavoriteShops() {
        Log.d(LOG_TAG, "showAllFavoriteShops()" );

       for(int i = 0; i<Session.getInstance().getMember().getFavoriteShops().size(); i++) {
             TextView textView = (TextView) findViewById(allTextViews[i]);
             Button deleteButton = (Button) findViewById(allDeleteButtons[i]);
             Button websiteButton  = (Button) findViewById(allWebsiteButtons[i]);

               if(textView.getText().toString().equals("")) {
                   textView.setText(Session.getInstance().getMember().getFavoriteShops().get(i).getName());
                   textView.setVisibility(View.VISIBLE);
                   deleteButton.setVisibility(View.VISIBLE);
                   websiteButton.setVisibility(View.VISIBLE);
               }
       }
        if(Session.getInstance().getMember().getFavoriteShops().isEmpty()) {
           TextView textView = (TextView) findViewById(R.id.warningTitle);
           textView.setText("Inga sparade favoritaffärer");
       }
    }

    /**
     * Listener for all website buttons for favorite shops.
     * @param view
     */
    public void onWebsiteShopClick(View view) {
        Log.d(LOG_TAG, "onWebsiteShopClick()");

        int index = 0;
        switch (view.getId()) {

            case R.id.websiteShop1:
                index = 0;
                break;
            case R.id.websiteShop2:
                index = 1;
                break;
            case R.id.websiteShop3:
                index = 2;
                break;
            case R.id.websiteShop4:
                index = 3;
                break;
            case R.id.websiteShop5:
                index = 4;
                break;
            case R.id.websiteShop6:
                index = 5;
                break;
            case R.id.websiteShop7:
                index = 6;
                break;
            case R.id.websiteShop8:
                index = 7;
                break;
            case R.id.websiteShop9:
                index = 8;
                break;
            case R.id.websiteShop10:
                index = 9;
                break;
            case R.id.websiteShop11:
                index = 10;
                break;
        }
        startWebsiteForFavoriteShop(index);
    }

    /**
     * Listener to all delete button for favorite shops.
     * @param view
     */
    public void onDeleteShopClick(View view) {
        Log.d(LOG_TAG, "onDeleteShopClick()");

        int index = 0;
        switch (view.getId()) {

            case R.id.deleteShop1:
                index = 0;
                break;
            case R.id.deleteShop2:
                index = 1;
                break;
            case R.id.deleteShop3:
                index = 2;
                break;
            case R.id.deleteShop4:
                index = 3;
                break;
            case R.id.deleteShop5:
                index = 4;
                break;
            case R.id.deleteShop6:
                index = 5;
                break;
            case R.id.deleteShop7:
                index = 6;
                break;
            case R.id.deleteShop8:
                index = 7;
                break;
            case R.id.deleteShop9:
                index = 8;
                break;
            case R.id.deleteShop10:
                index = 9;
                break;
            case R.id.deleteShop11:
                index = 10;
                break;
        }
        startPopUpActivity(index);
    }
}

package com.example.micka.demosalefinder;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles favorite shops
 */

public class ShopActivity extends AppCompatActivity {

    private static final String LOG_TAG = ShopActivity.class.getSimpleName();
    private ImageView webhallenFavorite, teknikmagasinetFavorite, hifiklubbenFavorite, kjellochcompanyFavorite, timberlandFavorite,
    eccoFavorite, scorettFavorite, hmFavorite, ginatricotFavorite, lindexFavorite, kappahlFavorite;
    public boolean imageResult;
    private MemberStorage memberStorage;

    /**
     * Initializes activity. Creates an instance
     * of interface MemberStorage by calling singleton
     * method and calls method to initialize views.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        memberStorage = DBMemberStorage.getInstance();
        initViews();
    }

    /*
     * Initializes views from activity_shop(XML)
     */
    private void initViews() {
        Log.d(LOG_TAG, "initViews()");
        webhallenFavorite = (ImageView)findViewById(R.id.favoriteWebhallen);
        teknikmagasinetFavorite = (ImageView)findViewById(R.id.favoriteTeknikmagasinet);
        hifiklubbenFavorite = (ImageView)findViewById(R.id.favoriteHifiklubben);
        kjellochcompanyFavorite = (ImageView)findViewById(R.id.favoriteKjellochcompany);
        timberlandFavorite = (ImageView)findViewById(R.id.favoriteTimberland);
        eccoFavorite = (ImageView)findViewById(R.id.favoriteEcco);
        scorettFavorite = (ImageView)findViewById(R.id.favoriteScorett);
        hmFavorite = (ImageView)findViewById(R.id.favoriteHm);
        ginatricotFavorite = (ImageView)findViewById(R.id.favoriteGinatricot);
        lindexFavorite = (ImageView)findViewById(R.id.favoriteLindex);
        kappahlFavorite = (ImageView)findViewById(R.id.favoriteKappahl);
    }

    /**
     * Listener on back button in XML and goes
     * back to previous activity in GUI.
     * @param view
     */
    public void onBackClick(View view) {
        Log.d(LOG_TAG, "backOnClick()");
        if (view.getId() == R.id.backButton) {
            Intent i = new Intent(ShopActivity.this, MapActivity.class);
            startActivity(i);
        }
    }

    /**
     * Listner for save button. If pressed,
     * it will check if list of favorite shops is
     * not empty and if true, it will save that list
     * to database and save return value in Session-
     * object. Then it starts MapActivity.
     * @param view
     */
    public void onSaveFavoriteShopsClick(View view) {
        Log.d(LOG_TAG, "onSaveFavoriteShopsClick()");

        if (view.getId() == R.id.save) {
            try {
                if(!Session.getInstance().getMember().getFavoriteShops().isEmpty()) {
                    Log.d(LOG_TAG, "Favorite shops: " + Session.getInstance().getMember().getFavoriteShops().toString());

                    Session.getInstance().getMember().setFavoriteShops(memberStorage.storeFavoriteShops(
                            Session.getInstance().getMember().getFavoriteShops()));
                }
            } catch (NullPointerException e) {
                Log.d(LOG_TAG, "No favorite shops stored, nullpointer exception " + e);
            }
        }
        Intent i = new Intent (ShopActivity.this, MapActivity.class);
        startActivity(i);
    }

    /**
     * Listener for shops markers. Will react if
     * button is pressed and add or delete shop
     * from list.
     * @param view
     */
    public void onFavoriteShopClick(View view) {
        Log.d(LOG_TAG, "onFavoriteWebhallenClick()");
        ImageView selectedFavorite = null;
        String selectedShop = null;

        switch (view.getId()) {

            case R.id.favoriteWebhallen:
                selectedFavorite = webhallenFavorite;
                selectedShop = "Webhallen";
                break;

            case R.id.favoriteTeknikmagasinet:
                selectedFavorite = teknikmagasinetFavorite;
                selectedShop = "Teknikmagasinet";
                break;

            case R.id.favoriteKjellochcompany:
                selectedFavorite = kjellochcompanyFavorite;
                selectedShop = "Kjell & Company";
                break;

            case R.id.favoriteHifiklubben:
                selectedFavorite = hifiklubbenFavorite;
                selectedShop = "Hi-Fi klubben";
                break;

            case R.id.favoriteTimberland:
                selectedFavorite = timberlandFavorite;
                selectedShop = "Timberland";
                break;

            case R.id.favoriteEcco:
                selectedFavorite = eccoFavorite;
                selectedShop = "Ecco";
                break;

            case R.id.favoriteScorett:
                selectedFavorite = scorettFavorite;
                selectedShop = "Scorett";
                break;

            case R.id.favoriteHm:
                selectedFavorite = hmFavorite;
                selectedShop = "HM";
                break;

            case R.id.favoriteGinatricot:
                selectedFavorite = ginatricotFavorite;
                selectedShop = "Ginatricot";
                break;

            case R.id.favoriteLindex:
                selectedFavorite = lindexFavorite;
                selectedShop = "Lindex";
                break;

            case R.id.favoriteKappahl:
                selectedFavorite = kappahlFavorite;
                selectedShop = "KappAhl";
                break;
        }

        imageResult = getImageResult(selectedFavorite);
        if(imageResult) {
            selectedFavorite.setImageDrawable(ContextCompat.getDrawable(MainActivity.getAppContext(),R.drawable.favoriteselected));
            addFavoriteShop(selectedShop);
        } if (!imageResult) {
            selectedFavorite.setImageDrawable(ContextCompat.getDrawable(MainActivity.getAppContext(),R.drawable.favoritesymbol));
            removeFavoriteShop(selectedShop);
        }
    }

    /*
     * Compares image from view with image
     * from drawable-folder to se if a shop
     * has been seleceted or unselected. Will
      * return true if images is equal.
     */
    private Boolean getImageResult(ImageView imageview) {
        Log.d(LOG_TAG, "getImageResult()");

        if (imageview.getDrawable().getConstantState() == getResources().getDrawable( R.drawable.favoritesymbol).getConstantState()) {
            imageResult = true;

        } else if (imageview.getDrawable().getConstantState() == getResources().getDrawable( R.drawable.favoriteselected).getConstantState()) {
            imageResult = false;
        }
        return imageResult;
    }

    /*
     * Loops and compares chosen shopName with
     * name from list that holds all available
     * shops. If equal, it will store shop in
     * list of favoriteShops for member.
     */
    private void addFavoriteShop(String name) {
        Log.d(LOG_TAG, "addFavoriteShop()");

        for(int i = 0; i<Session.getInstance().getStoredShops().size(); i++) {
            Shop shop = Session.getInstance().getStoredShops().get(i);

            if(shop.getName().equals(name)) {
                Session.getInstance().getMember().addFavoriteShop(shop);
            }
        }
    }

    /*
     * Loops and compares chosen shopName with
     * name from list that holds all available
     * shops. If equal, it will delete shop from
     * list of favoriteShops for member.
     */
    private void removeFavoriteShop(String name) {
        Log.d(LOG_TAG, "removeFavoriteShop()");

        for(int i = 0; i<Session.getInstance().getStoredShops().size(); i++) {
            Shop shop = Session.getInstance().getStoredShops().get(i);

            if(shop.getName().equals(name)) {
                Session.getInstance().getMember().removeFavoriteShop(shop);
            }
        }
    }
}

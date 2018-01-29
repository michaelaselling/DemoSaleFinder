package com.example.micka.demosalefinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Handles all filter functions for shops.
 */

public class FilterActivity extends AppCompatActivity {

    private static final String LOG_TAG = FilterActivity.class.getSimpleName();
    private ImageView clothesSelected, shoesSelected, electronicsSelected;
    private SeekBar sale;
    private TextView saleProgress, minSaleProgress;
    private static boolean filterClothes, filterShoes, filterElectronics;
    private static int progress_value = 0;

    /**
     * Initializes activity. Calls method to
     * initializes views from XML and sets
     * marker for filter selection invisible.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        initViews();
        setClothesSelectedInvisible();
        setShoesSelectedInvisible();
        setElectronicsSelectedInvisible();
        updateSalePercentage();
    }

    /*
     * Initializes views from XML file.
     */
    private void initViews() {
        clothesSelected = (ImageView) findViewById(R.id.clothesSelected);
        shoesSelected = (ImageView) findViewById(R.id.shoesSelected);
        electronicsSelected = (ImageView) findViewById(R.id.electronicsSelected);
        sale = (SeekBar) findViewById(R.id.sale);
        saleProgress = (TextView) findViewById(R.id.saleProgress);
        minSaleProgress = (TextView) findViewById(R.id.minSaleProgress);
    }

    /**
     * Listener on back button in XML and goes
     * back to previous activity in GUI.
     * @param view
     */
    public void onBackClick(View view) {
        Log.d(LOG_TAG, "onBackClick");

        if (view.getId() == R.id.backButton) {
            Intent i = new Intent(FilterActivity.this, MapActivity.class);
            startActivity(i);
        }
    }

    /**
     * Listener on clear filter button in XML.
     * Clears all chosen favorite shops if
     * button is pressed and sets sale percentage
     * to 0 in GUI.
     * @param view
     */
    public void onClearFilterClick(View view) {
        Log.d(LOG_TAG, "onClearFilterClick");

        if (view.getId() == R.id.clearFilter) {
           setClothesSelectedInvisible();
           setShoesSelectedInvisible();
           setElectronicsSelectedInvisible();
           sale.setProgress(0);
        }
    }

    /**
     * Listener on filter button in XML and
     * if pressed it will create another activity.
     * @param view
     */
    public void onFilterClick(View view) {
        Log.d(LOG_TAG, "filterOnClick()");

        if (view.getId() == R.id.filterButton) {
            Intent i = new Intent(FilterActivity.this, MapActivity.class);
            startActivity(i);
        }
    }

    /**
     * Updates sale percentage in GUI. Sets
     * seekbar progress value and adds
     * listener to view.
     */
    public void updateSalePercentage() {
        Log.d(LOG_TAG, "checkSalePercentage()");

        progress_value = 7;
        minSaleProgress.setVisibility(View.INVISIBLE);
        saleProgress.setText(sale.getProgress() + "%");
        sale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            /**
             * Listener to seekbar which updates the
             * progress of sale percentage when user
             * clicks on seekbar. Sets chosen sale
             * percentage status in GUI.
             * @param seekBar
             * @param progress
             * @param fromUser
             */

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progress_value = progress;
                    if(sale.getProgress() == 0) {
                        minSaleProgress.setVisibility(View.INVISIBLE);
                        saleProgress.setText("0%");
                    } else if (sale.getProgress() >= 1) {
                        minSaleProgress.setVisibility(View.VISIBLE);
                        saleProgress.setText(progress + "0%");
                    }
                }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // not needed
            }

            /**
             * Notifies when the user has stopped
             * clicking the seekbar. It will show
             * chosen sale percentage of users choice
             * in GUI.
             * @param seekBar
             */

            @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if(sale.getProgress() == 0) {
                        minSaleProgress.setVisibility(View.INVISIBLE);
                        saleProgress.setText("0%");
                    } else if (sale.getProgress() >= 1) {
                        minSaleProgress.setVisibility(View.VISIBLE);
                        saleProgress.setText(progress_value + "0%");
                    }
                }
            });
        }

    /**
     * Listener to clothes symbol and reacts by
     * adjusting the marker circle around it if
     * the symbol has been selected or unselected.
     * @param view
     */
    public void clothesOnClick(View view) {
        if (view.getId() == R.id.clothesSymbol){

            if(clothesSelected.isShown()) {
                setClothesSelectedInvisible();
            } else {
                setClothesSelectedVisible();
            }
        }
    }

    /**
     * Listener to shoes symbol and reacts by
     * adjusting the marker circle around it if
     * the symbol has been selected or unselected.
     * @param view
     */
    public void shoesOnClick(View view) {
        if (view.getId() == R.id.shoesSymbol){

            if(shoesSelected.isShown()) {
                setShoesSelectedInvisible();
            } else {
                setShoesSelectedVisible();
            }
        }
    }

    /**
     * Listener to electronics symbol and reacts by
     * adjusting the marker circle around it if
     * the symbol has been selected or unselected.
     * @param view
     */
    public void electronicsOnClick(View view) {
        if (view.getId() == R.id.electronicsSymbol){

            if(electronicsSelected.isShown()) {
                setElectronicsSelectedInvisible();
            } else {
                setElectronicsSelectedVisible();
            }
        }
    }

    /**
     * Is called from MapActivity and returns true
     * if filter by clothes category has been
     * selected.
     * @return
     */
    public static boolean filterForClothes() { return filterClothes; }

    /**
     * Is called from MapActivity and returns true
     * if filter by shoes category has been
     * selected.
     * @return
     */
    public static boolean filterForShoes() { return filterShoes; }

    /**
     * Is called from MapActivity and returns true
     * if filter by electronics category has been
     * selected.
     * @return
     */
    public static boolean filterForElectronics() { return filterElectronics; }

    /**
     * Is called from MapActivity and returns
     * sale percentage that user has chosen. If
     * user hasn't chosen any filter for sale, it
     * will set progress_value to 100, else it will
     * set progress_value to chosen sale percentage.
     * @return
     */
    public static int filterSalePercentage () {

        if(progress_value == 0) {
            progress_value = 100;
        } else if (progress_value > 0) {
            progress_value = progress_value * 10;
        }
        return progress_value;
    }

    /*
     * Sets markers for clothes symbols visible.
     */
    private void setClothesSelectedVisible() {
        clothesSelected.setVisibility(View.VISIBLE);
        filterClothes = true;
    }

    /*
     * Sets markers for clothes symbols invisible.
     */
    private void setClothesSelectedInvisible() {
        clothesSelected.setVisibility(View.INVISIBLE);
        filterClothes = false;
    }

    /*
     * Sets markers for shoes symbols visible.
     */
    private void setShoesSelectedVisible() {
        shoesSelected.setVisibility(View.VISIBLE);
        filterShoes = true;
    }

    /*
     * Sets markers for shoes symbols invisible.
     */
    private void setShoesSelectedInvisible() {
        shoesSelected.setVisibility(View.INVISIBLE);
        filterShoes = false;
    }

    /*
     * Sets markers for electronics symbols visible.
     */
    private void setElectronicsSelectedVisible() {
        electronicsSelected.setVisibility(View.VISIBLE);
        filterElectronics = true;
    }

    /*
     * Sets markers for electronics symbols invisible.
     */
    private void setElectronicsSelectedInvisible() {
        electronicsSelected.setVisibility(View.INVISIBLE);
        filterElectronics = false;
    }
}

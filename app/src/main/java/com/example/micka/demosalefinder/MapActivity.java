package com.example.micka.demosalefinder;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/*
* Creates map and shows available shops
*/
public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private static final String LOG_TAG = MapActivity.class.getSimpleName();
    private GoogleMap mMap;
    private List<Shop> allShops, shopsByShopType;
    private ShopStorage shopStorage;
    public int salePercentage = FilterActivity.filterSalePercentage();

    CustomMarkerAdapter customMarkerAdapter = new CustomMarkerAdapter(MainActivity.getAppContext());

    /**
     * Initializes activity and creates map. Creates
     * an instance of interface ShopStorage by calling
     * singleton method. Calls method to check if
     * all shops are stored.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        shopStorage = DBShopStorage.getInstance();
        checkIfShopsAreStored();
    }

    /**
     * Is called when map is ready to be used. Sets
     * max and min zoom preference and calls method
     * to check if user wants any filters. Creates
     * listener for map.
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(LOG_TAG, "onMapReady()");

        mMap = googleMap;
        mMap.setMinZoomPreference(13.0f);
        mMap.setMaxZoomPreference(18.0f);
        checkForFilters();
        mMap.setOnInfoWindowClickListener(this);
    }

    /**
     * Checks if user has chosen any filters for
     * shops. If true, it calls on methods to
     * create markers for shops.
     */
    public void checkForFilters() {
        Log.d(LOG_TAG, "checkForFilters()");

        if(FilterActivity.filterForClothes()) {
           getShopsByShopType(ShopType.CLOTHES);
           getShopTypesFromShops(shopsByShopType);

        } if(FilterActivity.filterForShoes()) {
            getShopsByShopType(ShopType.SHOES);
            getShopTypesFromShops(shopsByShopType);

        } if(FilterActivity.filterForElectronics()) {
            getShopsByShopType(ShopType.ELECTRONICS);
            getShopTypesFromShops(shopsByShopType);

        } if(!FilterActivity.filterForClothes() && !FilterActivity.filterForShoes() && !FilterActivity.filterForElectronics()) {
            getShopsByShopType(null);
            getShopTypesFromShops(shopsByShopType);
        }
    }

    /**
     * Checks if Enum is null and if true,
     * it will save allShops into shopsByShopType
     * to indicate that there's no filter. Else
     * it will loop through allShops and save
     * shops which has the same Enum as the parameter.
     * @param shopType
     */
    private void getShopsByShopType(Enum shopType) {
        shopsByShopType = new ArrayList<Shop>();

        if(shopType == null) {
            shopsByShopType = allShops;

        } else {
            for(int i = 0; i < allShops.size(); i++) {
                if(allShops.get(i).getShopType().equals(shopType)) {
                    shopsByShopType.add(allShops.get(i));
                }
            }
        }
    }

    /**
     * Listener for zoom in button and increase
     * zoom preference for map.
     * @param view
     */
    public void onZoomInClick(View view) {
        Log.d(LOG_TAG, "onZoomInClick()");
        if (view.getId() == R.id.zoomIn) {
            float zoom = mMap.getCameraPosition().zoom;
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
        }
    }

    /**
     * Listener for zoom out button and decrease
     * zoom preference for map.
     * @param view
     */
    public void onZoomOutClick(View view) {
        Log.d(LOG_TAG, "onZoomOutClick()");
        if (view.getId() == R.id.zoomOut) {
            float zoom = mMap.getCameraPosition().zoom;
            mMap.animateCamera(CameraUpdateFactory.zoomOut());
        }
    }

    /**
     * Listener for account button and starts AccountActivity.
     * @param view
     */
    public void onAccountClick(View view) {
        Log.d(LOG_TAG, "onAccountClick()");
        Intent i = new Intent(MapActivity.this, AccountActivity.class);
        startActivity(i);
    }

    /**
     * Listener for filter button and starts FilterActivity.
     * @param view
     */
    public void onFilterClick(View view) {
        Log.d(LOG_TAG, "onFilterClick()");
        Intent i = new Intent(MapActivity.this, FilterActivity.class);
        startActivity(i);
    }

    /**
     * Listener for favorite shop button and starts ShopActivity.
     * @param view
     */
    public void onFavoriteShopsClick(View view) {
        Log.d(LOG_TAG, "onFavoriteShopsClick()");
        if (view.getId() == R.id.favoriteSymbol) {
            Intent i = new Intent(MapActivity.this, ShopActivity.class);
            startActivity(i);
        }
    }

    /*
     * Creates and returns all shops which
     * will be placed in map.
     *
     */
    private List<Shop> createAllShops() {
        Log.d(LOG_TAG, "createAllShops()");
        //Clothes
        Shop hm = new Shop("H&M", "Kungsgatan 55-57",20, ShopType.CLOTHES, 57.704543, 11.967982, "http://www.hm.com/se/");
        Shop ginatricot = new Shop("Ginatricot", "Fredsgatan 10",25, ShopType.CLOTHES, 57.704146, 11.964829, "https://www.ginatricot.com/se/sv/start");
        Shop kappahl = new Shop("KappAhl", "Kungsgatan 44", 20, ShopType.CLOTHES, 57.704663, 11.967082, "https://www.kappahl.com/sv-SE/");
        Shop lindex = new Shop("Lindex", "Kungsgatan 50", 10, ShopType.CLOTHES, 57.706394, 11.970301, "https://www.lindex.com/se/");

        //Electronics
        Shop webhallen = new Shop("Webhallen","Ekelundsgatan 4", 10, ShopType.ELECTRONICS, 57.704605, 11.961269, "https://new.webhallen.com");
        Shop hifiklubben = new Shop("Hi-Fi klubben", "Kungsgatan 28", 15, ShopType.ELECTRONICS,  57.703844, 11.961868, "https://www.hifiklubben.se");
        Shop kjellochcompany = new Shop("Kjell & Company", "Kungsgatan 27", 10, ShopType.ELECTRONICS,  57.704070, 11.964293, "https://www.kjell.com/se/");
        Shop teknikmagasinet = new Shop("Teknikmagasinet", "Södra Hamngatan 37-41", 15, ShopType.ELECTRONICS,  57.706566, 11.968992, "https://www.teknikmagasinet.se");

        //Shoes
        Shop scorett = new Shop("Scorett", "Korsgatan 15,", 15, ShopType.SHOES, 57.704119, 11.966569, "http://www.scorett.se");
        Shop timberland = new Shop("Timberland", "Korsgatan 16", 10, ShopType.SHOES, 57.704720, 11.966492, "https://www.timberland.se");
        Shop ecco = new Shop("Ecco", "Kungsgatan 44", 20, ShopType.SHOES, 57.704686, 11.967243, "https://se.ecco.com/sv-SE");


        allShops = new ArrayList<Shop>();
        allShops.add(hm);
        allShops.add(ginatricot);
        allShops.add(kappahl);
        allShops.add(lindex);
        allShops.add(webhallen);
        allShops.add(hifiklubben);
        allShops.add(kjellochcompany);
        allShops.add(teknikmagasinet);
        allShops.add(scorett);
        allShops.add(timberland);
        allShops.add(ecco);

        Log.d(LOG_TAG, allShops.toString());
        return allShops;
    }

    /**
     * Creates symbols (BitMap) for marker after shopType.
     * Loops through list of shops and check what kind of
     * shopType each shop has and calls on createMarker().
     *
      * @param shopsByShopType
     */
    private void getShopTypesFromShops(List<Shop> shopsByShopType) {
        BitmapDescriptor bitmapDescriptorClothes = BitmapDescriptorFactory.fromResource(R.drawable.clothesmarker);
        BitmapDescriptor bitmapDescriptorShoes = BitmapDescriptorFactory.fromResource(R.drawable.shoesmarker);
        BitmapDescriptor bitmapDescriptorElectronics = BitmapDescriptorFactory.fromResource(R.drawable.electronicsmarker);

        for(int i = 0; i < shopsByShopType.size(); i++) {
            Shop shop = shopsByShopType.get(i);

            if(salePercentage > shop.getSalePercentage() || salePercentage == shop.getSalePercentage()) {
                mMap.setInfoWindowAdapter(customMarkerAdapter);
                String title = shop.getName() + "\n";
                String snippet = shop.getAddress() + " \n Rea: " + shop.getSalePercentage()+ "%\n" +
                        "Till hemsidan!";
                LatLng position = new LatLng(shop.getLatitude(), shop.getLongitude());

                switch (shop.getShopType()) {
                    case CLOTHES:
                        createMarker(bitmapDescriptorClothes, position, title, snippet);
                        break;
                    case SHOES:
                        createMarker(bitmapDescriptorShoes, position, title, snippet);
                        break;
                    case ELECTRONICS:
                        createMarker(bitmapDescriptorElectronics, position, title, snippet);
                        break;
                }
            }
        }
    }

    /**
     * Sets all markers for shops by shopType.
     * @param bitmapDescriptor
     * @param position
     * @param title
     * @param snippet
     */
    private void createMarker(BitmapDescriptor bitmapDescriptor, LatLng position, String title, String snippet) {
        Log.d(LOG_TAG, "createMarker()");
        mMap.addMarker(new MarkerOptions().icon(bitmapDescriptor)
                .position(position)
                .title(title)
                .snippet(snippet));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }

    /**
     * Listener to info-window. If an info-window
     * is pressed it will get the name of the shop
     * in the info-window, get the website
     * for that shop and start website in new activity.
     * @param marker
     */
    @Override
    public void onInfoWindowClick(Marker marker) {

    String title = customMarkerAdapter.getInfoTitle();
    Log.d(LOG_TAG, "Affärens namn: "+ title);

        for(int i = 0; i < allShops.size(); i++) {
            Shop shop = allShops.get(i);

            if(title.equals(shop.getName() + "\n")) {
                String website = shop.getWebsite();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                startActivity(browserIntent);
            }
        }
    }

    /**
     * Checks if list of shops is empty, if true
     * it means that shops haven't been stored
     * and it will call interface method to store
     * all shops. If false, it will store all
     * shops in Session-object.
     */
    private void checkIfShopsAreStored() {
        allShops = shopStorage.getAllShops();
        if (allShops.isEmpty()) {
            allShops = createAllShops();
            Session.getInstance().setStoredShops(shopStorage.storeAllShops(allShops));
            Log.d(LOG_TAG, "CreateAllShops(), shops are stored for the first time" + Session.getInstance().getStoredShops());
        } else {
            Session.getInstance().setStoredShops(allShops);
            Log.d(LOG_TAG, "Shops are already stored once" + Session.getInstance().getStoredShops());
        }
    }
}

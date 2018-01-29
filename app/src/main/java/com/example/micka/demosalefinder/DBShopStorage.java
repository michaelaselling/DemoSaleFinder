package com.example.micka.demosalefinder;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 *  Handles everything with shops trough database.
 */

public class DBShopStorage implements ShopStorage {

    private static final String LOG_TAG = DBShopStorage.class.getSimpleName();
    private static DBStorage dbStorage;
    private static ShopStorage shopStorage;

    /*
     * Hides contructor so that no other class
     * can create DBShopStorage object.
     * Use singelton method instead.
     */
    private DBShopStorage() {
        Log.d(LOG_TAG, "DBShopStorage()");

        // hide constructor.
    }

    /**
     * Returns a singelton instance of this class.
     */
    public static ShopStorage getInstance() {
        Log.d(LOG_TAG, "getInstance()");

        if(dbStorage == null) {

            dbStorage = DBStorage.getInstance();
        }
        if(shopStorage == null) {

            shopStorage = new DBShopStorage();
        }
        return shopStorage;
    }

    /**
     * Returns a list of all stored shops.
     */
    @Override
    public List<Shop> getAllShops() {
        Log.d(LOG_TAG, "getAllShops()");

        List<Shop> shops = new ArrayList<>();
        Cursor cursor = dbStorage.query(ShopStorageHelper.TABLE_SHOP,
                ShopStorageHelper.ALL_SHOP_COLUMNS);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Shop shop = cursorToShop(cursor);
            Log.d(LOG_TAG, "Adding :" + shop);
            shops.add(shop);
            cursor.moveToNext();
        }
        cursor.close();
        return shops;
    }

    /**
     * Stores and returns all shops in database.
     * @param allShops
     * @return
     */
    @Override
    public List<Shop> storeAllShops(List<Shop> allShops) {
        Log.d(LOG_TAG, "storeAllShops()");

        List<Shop> storedShops = new ArrayList<Shop>();

        for(int i = 0; i < allShops.size(); i++) {
            Log.d(LOG_TAG, "createAllShops() + addMember: " + allShops.get(i));
            Shop shop = allShops.get(i);
            ContentValues values = new ContentValues();
            values.put(ShopStorageHelper.COLUMN_NAME, shop.getName());
            values.put(ShopStorageHelper.COLUMN_ADDRESS, shop.getAddress());
            values.put(ShopStorageHelper.COLUMN_SALE_PERCENTAGE, shop.getSalePercentage());
            Enum tempShopType = shop.getShopType();
            String shopType = ShopType.checkShopType(tempShopType);
            values.put(ShopStorageHelper.COLUMN_SHOP_TYPE, shopType);
            values.put(ShopStorageHelper.COLUMN_LATITUDE, shop.getLatitude());
            values.put(ShopStorageHelper.COLUMN_LONGITUDE, shop.getLongitude());
            values.put(ShopStorageHelper.COLUMN_WEBSITE, shop.getWebsite());

            long insertId =  dbStorage.insert(ShopStorageHelper.TABLE_SHOP, values);
            Cursor cursor =  dbStorage.query(ShopStorageHelper.TABLE_SHOP,
                    ShopStorageHelper.ALL_SHOP_COLUMNS, ShopStorageHelper.COLUMN_SHOP_ID + " = " + insertId);
            cursor.moveToFirst();
            shop = cursorToShop(cursor);
            storedShops.add(shop);
            cursor.close();
        }
        Log.d(LOG_TAG, storedShops.toString());
        return storedShops;
    }

    /*
     * Gets values from cursor and returns a new shop.
     */
    private Shop cursorToShop(Cursor cursor) {
        Log.d(LOG_TAG, "cursorToShop()");

        int id = cursor.getInt(cursor.getColumnIndex(ShopStorageHelper.COLUMN_SHOP_ID));
        String name = cursor.getString(cursor.getColumnIndex(ShopStorageHelper.COLUMN_NAME));
        String address = cursor.getString(cursor.getColumnIndex(ShopStorageHelper.COLUMN_ADDRESS));
        int salePercentage = cursor.getInt(cursor.getColumnIndex(ShopStorageHelper.COLUMN_SALE_PERCENTAGE));
        String tempShopType = cursor.getString(cursor.getColumnIndex(ShopStorageHelper.COLUMN_SHOP_TYPE));
        double latitude = cursor.getDouble(cursor.getColumnIndex(ShopStorageHelper.COLUMN_LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndex(ShopStorageHelper.COLUMN_LONGITUDE));
        String website = cursor.getString(cursor.getColumnIndex(ShopStorageHelper.COLUMN_WEBSITE));

        ShopType shopType = ShopType.valueOf(tempShopType);
        return new Shop(id, name, address, salePercentage, shopType, latitude, longitude, website);
    }
}

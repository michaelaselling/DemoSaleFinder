package com.example.micka.demosalefinder;

/**
 * Creates tables for Shop
 */

public class ShopStorageHelper {

    private static final String LOG_TAG = ShopStorageHelper.class.getSimpleName();

    public static final String TABLE_SHOP = "shop";
    public static final String COLUMN_SHOP_ID = "shop_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_SALE_PERCENTAGE = "sale_percentage";
    public static final String COLUMN_SHOP_TYPE = "shop_type";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_WEBSITE = "website";

    public static String[] ALL_SHOP_COLUMNS = {
            ShopStorageHelper.COLUMN_SHOP_ID,
            ShopStorageHelper.COLUMN_NAME,
            ShopStorageHelper.COLUMN_ADDRESS,
            ShopStorageHelper.COLUMN_SALE_PERCENTAGE,
            ShopStorageHelper.COLUMN_SHOP_TYPE,
            ShopStorageHelper.COLUMN_LATITUDE,
            ShopStorageHelper.COLUMN_LONGITUDE,
            ShopStorageHelper.COLUMN_WEBSITE,
            };

    // Database creation sql statement
    public static final String CREATE_SHOP_TABLE = "create table "
            + TABLE_SHOP + "( " + COLUMN_SHOP_ID
            + " integer primary key autoincrement, " + COLUMN_NAME
            + " text not null, " + COLUMN_ADDRESS
            + " text not null, " + COLUMN_SALE_PERCENTAGE
            + " integer, " + COLUMN_SHOP_TYPE
            + " text not null, " + COLUMN_LATITUDE
            + " real, " + COLUMN_LONGITUDE
            + " real, " + COLUMN_WEBSITE
            + " text not null);";
}
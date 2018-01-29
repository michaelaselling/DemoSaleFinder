package com.example.micka.demosalefinder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Initializes tables and columns for Member and MemberFavoriteShop
 */

public class MemberStorageHelper {

    public static final String TABLE_MEMBER = "member";
    public static final String COLUMN_MEMBER_ID = "member_id";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_EMAIl = "email";
    public static final String COLUMN_PASSWORD = "password";

    public static final String TABLE_MEMBER_FAVORITE_SHOP = "favorite_shop";
    public static final String COLUMN_FAVORITE_MEMBER_ID = "member_id";
    public static final String COLUMN_FAVORITE_SHOP_ID = "shop_id";

    public static String[] ALL_MEMBER_COLUMNS = {
            MemberStorageHelper.COLUMN_MEMBER_ID,
            MemberStorageHelper.COLUMN_FIRSTNAME,
            MemberStorageHelper.COLUMN_LASTNAME,
            MemberStorageHelper.COLUMN_EMAIl,
            MemberStorageHelper.COLUMN_PASSWORD};

    // Database creation sql statement
    public static final String CREATE_MEMBER_TABLE = "create table "
            + TABLE_MEMBER + "( " + COLUMN_MEMBER_ID
            + " integer primary key autoincrement, " + COLUMN_FIRSTNAME
            + " text not null, " + COLUMN_LASTNAME
            + " text not null, " + COLUMN_EMAIl
            + " text not null, " + COLUMN_PASSWORD
            + " text not null);";

    // Database creation sql statement
    public static final String CREATE_MEMBER_FAVORITE_SHOP_TABLE = "create table "
            + TABLE_MEMBER_FAVORITE_SHOP + "( " + COLUMN_FAVORITE_MEMBER_ID
            + " integer, " + COLUMN_FAVORITE_SHOP_ID
            + " integer, PRIMARY KEY (" + COLUMN_FAVORITE_MEMBER_ID + "," + COLUMN_FAVORITE_SHOP_ID + "));";
}

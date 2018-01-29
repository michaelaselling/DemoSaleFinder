package com.example.micka.demosalefinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.util.Log;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles everything with members including favorite shops, trough database.
 */

public class DBMemberStorage implements MemberStorage {

    private static final String LOG_TAG = DBMemberStorage.class.getSimpleName();
    private static DBStorage dbStorage;
    private static MemberStorage memberStorage;

    /*
     * Hides contructor so that no other class
     * can create DBMemberStorage object.
     * Use singelton method instead.
     */
    private DBMemberStorage() {
        // hide constructor.
    }

    /**
     * Returns a singelton instance of this class.
     */
    public static MemberStorage getInstance() {
        if(dbStorage == null) {

            dbStorage = DBStorage.getInstance();
        }
        if(memberStorage == null) {

            memberStorage = new DBMemberStorage();
        }
        return memberStorage;
    }

    /**
     * Returns a list of all stored members.
     */
    @Override
    public List<Member> getAllMembers() {
        Log.d(LOG_TAG, "getAllMembers()");
        List<Member> allMembers = new ArrayList<>();

        Cursor cursor = dbStorage.query(MemberStorageHelper.TABLE_MEMBER,
                MemberStorageHelper.ALL_MEMBER_COLUMNS);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Member member = cursorToMember(cursor);
            Log.d(LOG_TAG, "Adding :" + member);
            allMembers.add(member);
            cursor.moveToNext();
        }
        cursor.close();
        return allMembers;
    }

    /**
     * Stores and returns a list of all stored
     * favorite shops for current member.
     */
   @Override
    public List<Shop> getAllFavoriteShops() {
        Log.d(LOG_TAG, "getAllFavoriteShops()");
        List<Shop> favoriteShops = new ArrayList<>();

        //Query to get favorite shops for current member
        String rawQuery = "SELECT " + ShopStorageHelper.TABLE_SHOP + "." + ShopStorageHelper.COLUMN_SHOP_ID + " , " +
               ShopStorageHelper.TABLE_SHOP + "." + ShopStorageHelper.COLUMN_NAME + " , " +
               ShopStorageHelper.TABLE_SHOP + "." + ShopStorageHelper.COLUMN_ADDRESS + " , " +
               ShopStorageHelper.TABLE_SHOP + "." + ShopStorageHelper.COLUMN_SALE_PERCENTAGE + " , " +
               ShopStorageHelper.TABLE_SHOP + "." + ShopStorageHelper.COLUMN_SHOP_TYPE + " , " +
               ShopStorageHelper.TABLE_SHOP + "." + ShopStorageHelper.COLUMN_LATITUDE + " , " +
               ShopStorageHelper.TABLE_SHOP + "." + ShopStorageHelper.COLUMN_LONGITUDE + " , " +
               ShopStorageHelper.TABLE_SHOP + "." + ShopStorageHelper.COLUMN_WEBSITE + " FROM " +
               ShopStorageHelper.TABLE_SHOP + " JOIN " + MemberStorageHelper.TABLE_MEMBER_FAVORITE_SHOP +
               " ON " + ShopStorageHelper.TABLE_SHOP + "." + ShopStorageHelper.COLUMN_SHOP_ID + " = " +
               MemberStorageHelper.TABLE_MEMBER_FAVORITE_SHOP + "." + MemberStorageHelper.COLUMN_FAVORITE_SHOP_ID + " WHERE " +
               MemberStorageHelper.COLUMN_MEMBER_ID + " = " + Session.getInstance().getMember().getId();

        Cursor cursor = dbStorage.rawQuery(rawQuery, null);
        cursor.moveToFirst();
        favoriteShops = cursorToShop(cursor);
        cursor.close();
        Log.d(LOG_TAG, "All stored shops: " + favoriteShops.toString());

        return favoriteShops;
    }

    /**
     * Deletes favorite shop and returns boolean
     * if the shop was deleted properly or not.
     * @param shop
     * @return
     */
    @Override
    public boolean deleteFavoriteShop(Shop shop) {
        Log.d(LOG_TAG, "deleteFavoriteShop()");
        Boolean favoriteShopDeleted = true;

        String deleteWhere = MemberStorageHelper.COLUMN_FAVORITE_SHOP_ID + " = " + shop.getId();

        int deletedRows = dbStorage.delete(MemberStorageHelper.TABLE_MEMBER_FAVORITE_SHOP, deleteWhere);

        if(deletedRows == 0) {
            favoriteShopDeleted = false;
        }
        List<Shop> updatedFavoriteShops = getAllFavoriteShops();
        Session.getInstance().getMember().setFavoriteShops(updatedFavoriteShops);

        return favoriteShopDeleted;
    }

    /**
     * Deletes all currents favorite shops first and then
     * creates a new list of all favorite shops for
     * current member and saves list in Session object.
     * @param favoriteShops
     * @return
     */
    @Override
    public List<Shop> storeFavoriteShops(List<Shop> favoriteShops) {
        Log.d(LOG_TAG, "storeFavoriteShops()");
        deleteFavoriteShops();

        for(int i = 0; i<favoriteShops.size(); i++) {
            Shop shop = favoriteShops.get(i);
            ContentValues values = new ContentValues();
            values.put(MemberStorageHelper.COLUMN_MEMBER_ID, Session.getInstance().getMember().getId());
            values.put(ShopStorageHelper.COLUMN_SHOP_ID, shop.getId());
            dbStorage.insert(MemberStorageHelper.TABLE_MEMBER_FAVORITE_SHOP, values);
        }

        List<Shop> savedFavoriteShops = getAllFavoriteShops();
        Session.getInstance().getMember().setFavoriteShops(savedFavoriteShops);

        return savedFavoriteShops;
    }

    /*
     * Deletes all stored favorite shops for
     * current member.
     */
    private void deleteFavoriteShops() {
        Log.d(LOG_TAG, "deleteFavoriteShops()");

        String deleteWhere = MemberStorageHelper.COLUMN_FAVORITE_MEMBER_ID + " = " + Session.getInstance().getMember().getId();
        int deletedRows = dbStorage.delete(MemberStorageHelper.TABLE_MEMBER_FAVORITE_SHOP, deleteWhere);
        Log.d(LOG_TAG, "Number of rows deleted: " + deletedRows);
    }

    /**
     * Stores and returns a new member with an
     * autoincremented ID from database.
     * @param member
     * @return
     */
    @Override
    public Member storeMember(Member member) {
        Log.d(LOG_TAG, "storeMember() + addMember: " + member);
        ContentValues values = new ContentValues();
        values.put(MemberStorageHelper.COLUMN_FIRSTNAME, member.getFirstName());
        values.put(MemberStorageHelper.COLUMN_LASTNAME, member.getLastName());
        values.put(MemberStorageHelper.COLUMN_EMAIl, member.getEmail());
        values.put(MemberStorageHelper.COLUMN_PASSWORD, member.getPassword());

            long insertId =  dbStorage.insert(MemberStorageHelper.TABLE_MEMBER,  values);
            Cursor cursor =  dbStorage.query(MemberStorageHelper.TABLE_MEMBER,
                    MemberStorageHelper.ALL_MEMBER_COLUMNS, MemberStorageHelper.COLUMN_MEMBER_ID + " = " + insertId);

            cursor.moveToFirst();
            Member newMember = cursorToMember(cursor);
            cursor.close();

            return newMember;
    }

    /*
     * Gets values from cursor and returns a
     * list of all stored favorite shops.
     */
    private List<Shop> cursorToShop(Cursor cursor) {
        Log.d(LOG_TAG, "cursorToShop()");
        List<Shop> favoriteShops = new ArrayList<Shop>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndex(ShopStorageHelper.COLUMN_SHOP_ID));
            String name = cursor.getString(cursor.getColumnIndex(ShopStorageHelper.COLUMN_NAME));
            String address = cursor.getString(cursor.getColumnIndex(ShopStorageHelper.COLUMN_ADDRESS));
            int salePercentage = cursor.getInt(cursor.getColumnIndex(ShopStorageHelper.COLUMN_SALE_PERCENTAGE));
            String tempShopType = cursor.getString(cursor.getColumnIndex(ShopStorageHelper.COLUMN_SHOP_TYPE));
            Double latitude = cursor.getDouble(cursor.getColumnIndex(ShopStorageHelper.COLUMN_LATITUDE));
            Double longitude = cursor.getDouble(cursor.getColumnIndex(ShopStorageHelper.COLUMN_LONGITUDE));
            String webiste = cursor.getString(cursor.getColumnIndex(ShopStorageHelper.COLUMN_WEBSITE));

            ShopType shopType = ShopType.valueOf(tempShopType);

            Shop shop = new Shop(id, name,address, salePercentage, shopType, latitude, longitude, webiste);
            favoriteShops.add(shop);
        }
        return favoriteShops;
    }

    /*
     * Gets values from cursor and returns a new member.
     */
    private Member cursorToMember(Cursor cursor) {
        Log.d(LOG_TAG, "cursorToMember()");
            int id = cursor.getInt(cursor.getColumnIndex(MemberStorageHelper.COLUMN_MEMBER_ID));
            String firstName = cursor.getString(cursor.getColumnIndex(MemberStorageHelper.COLUMN_FIRSTNAME));
            String lastName = cursor.getString(cursor.getColumnIndex(MemberStorageHelper.COLUMN_LASTNAME));
            String email = cursor.getString(cursor.getColumnIndex(MemberStorageHelper.COLUMN_EMAIl));
            String password = cursor.getString(cursor.getColumnIndex(MemberStorageHelper.COLUMN_PASSWORD));

            return new Member(id, firstName, lastName, email, password);
    }
}
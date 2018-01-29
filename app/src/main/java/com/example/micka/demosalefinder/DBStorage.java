package com.example.micka.demosalefinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Creates database.
 */

public class DBStorage extends SQLiteOpenHelper {

    private static final String LOG_TAG = MemberStorageHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "salefinder.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase database;
    private static DBStorage dbStorage;

    /*
     * Initialize database in constructor.
     */
    private DBStorage(Context context) throws SQLException {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(LOG_TAG, "DBStorage()");
        try {
            database = getWritableDatabase();
        } catch (SQLException e) {
            Log.d(LOG_TAG, "Unable to get writable database, close()");
            close();
        }
    }

    /**
     * Returns a singelton instance of this class.
     */
    public static DBStorage getInstance() {
        if(dbStorage == null) {

            dbStorage = new DBStorage(MainActivity.getAppContext());
        }
        return dbStorage;
    }

    /**
     *  Initializes activity and creates tables in database.
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(LOG_TAG, "onCreate()");
        System.out.println(MemberStorageHelper.CREATE_MEMBER_TABLE);
        sqLiteDatabase.execSQL(MemberStorageHelper.CREATE_MEMBER_TABLE);
        System.out.println(ShopStorageHelper.CREATE_SHOP_TABLE);
        sqLiteDatabase.execSQL(ShopStorageHelper.CREATE_SHOP_TABLE);
        System.out.println(MemberStorageHelper.CREATE_MEMBER_FAVORITE_SHOP_TABLE);
        sqLiteDatabase.execSQL(MemberStorageHelper.CREATE_MEMBER_FAVORITE_SHOP_TABLE);
    }

    /**
     * Is called when database wants to be updated.
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d(LOG_TAG,
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MemberStorageHelper.TABLE_MEMBER + ShopStorageHelper.TABLE_SHOP);
        onCreate(sqLiteDatabase);
    }

    /**
     *  Executes delete-query given the table and whereClause.
     * @param table
     * @param whereClause
     * @return
     */
    public int delete(String table, String whereClause) {
        return database.delete(table, whereClause, null);
    }

    /**
     * Executes rawQuery given rawQuery and selectionsArgs.
     * @param rawQuery
     * @param selectionArgs
     * @return
     */
    public Cursor rawQuery(String rawQuery, String[] selectionArgs) {
        return database.rawQuery(rawQuery, selectionArgs);
    }

    /**
     * Executes query given the table, columns and selection.
     * @param table
     * @param columns
     * @param selection
     * @return
     */
    public Cursor query (String table, String[] columns, String selection) {
        return database.query(table, columns, selection, null, null, null, null);
    }

    /**
     * Executes query given the table and columns.
     * @param table
     * @param columns
     * @return
     */
    public Cursor query (String table, String[] columns) {
        return query(table, columns, null);
    }

    /**
     * Executes insert-query given the table and values.
     * @param table
     * @param values
     * @return
     */
    public long insert (String table, ContentValues values) {
        return database.insert(table,null, values);
    }

}

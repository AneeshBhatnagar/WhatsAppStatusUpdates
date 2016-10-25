package com.slashcoding.whatsappstatus;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// Static Database Variables
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "StatusUpdates";
	public static final String TABLE_CATEGORIES = "categories";
	public static final String TABLE_MESSAGES = "messages";

	// Column Names for Category Table
	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_COUNT = "count";

	// Column Names for Messages Table
	public static final String KEY_MESSAGE = "status";
	public static final String KEY_CATID = "catid";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// CREATING DATABASE TABLE
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CATEGORIES_TABLE = "CREATE TABLE IF NOT EXISTS "
				+ TABLE_CATEGORIES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_NAME + " TEXT" + ")";
		db.execSQL(CREATE_CATEGORIES_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);

		// Create tables again
		onCreate(db);
	}

	// Function to Get all Categories in HashMap
	public ArrayList<HashMap<String, String>> getAllCategoriesmap() {
		ArrayList<HashMap<String, String>> catList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT  * FROM " + TABLE_CATEGORIES
				+ " ORDER BY " + KEY_NAME;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				String selectQ = "SELECT * FROM " + TABLE_MESSAGES + " WHERE "
						+ KEY_CATID + "=" + cursor.getString(0);
				Cursor cursor2 = db.rawQuery(selectQ, null);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(KEY_ID, cursor.getString(0));
				map.put(KEY_NAME, cursor.getString(1));
				map.put(KEY_COUNT, String.valueOf(cursor2.getCount()));
				catList.add(map);
			} while (cursor.moveToNext());
		}

		return catList;
	}

	public ArrayList<HashMap<String, String>> getCategoryMessages(int id) {
		ArrayList<HashMap<String, String>> msgList = new ArrayList<HashMap<String, String>>();
		String selectQ = "SELECT * FROM " + TABLE_MESSAGES + " WHERE "
				+ KEY_CATID + "=" + String.valueOf(id) + " ORDER BY RANDOM()";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQ, null);
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(KEY_ID, cursor.getString(0));
				map.put(KEY_MESSAGE, cursor.getString(1));
				msgList.add(map);
			} while (cursor.moveToNext());
		}
		return msgList;
	}
}

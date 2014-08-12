// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.
package edu.oit.cst407.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class implements the SQLite Database.
 * @author Leander Rodriguez
 * @version 1.0
 *
 */
// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

	/////////////////////////////////////////////////////////////////////
	//	Constants & Data
	/////////////////////////////////////////////////////////////////////
	// For logging:
	private static final String TAG = "DBAdapter";
	
	// DB Fields
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	/*
	 * CHANGE 1:
	 */
	// TODO: Setup your fields here:
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LONGITUDE = "longitude";
	public static final String KEY_DATE = "date";
	public static final String KEY_TIME = "time";
	public static final String KEY_MIN_AGE = "minAge";
	public static final String KEY_MAX_AGE = "maxAge";
	public static final String KEY_SKILL_LEVEL = "skillLevel";
	public static final String KEY_GENDER = "gender";
	public static final String KEY_PITCH = "pitch";
	public static final String KEY_GAME_TYPE = "gameType";
	public static final String KEY_PLAYERS = "players";
	
	
	// TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
	public static final int COL_LATITUDE = 1;
	public static final int COL_LONGITUDE = 2;
	public static final int COL_DATE = 3;
	public static final int COL_TIME = 4;
	public static final int COL_MIN_AGE = 5;
	public static final int COL_MAX_AGE = 6;
	public static final int COL_SKILL_LEVEL = 7;
	public static final int COL_GENDER = 8;
	public static final int COL_PITCH = 9;
	public static final int COL_GAME_TYPE = 10;
	public static final int COL_PLAYERS = 11;
	
	public static final String[] ALL_KEYS = new String[]{ KEY_ROWID, 
														  KEY_LATITUDE, 
														  KEY_LONGITUDE, 
														  KEY_DATE, 
														  KEY_TIME, 
														  KEY_MIN_AGE, 
														  KEY_MAX_AGE, 
														  KEY_SKILL_LEVEL, 
														  KEY_GENDER, 
														  KEY_PITCH, 
														  KEY_GAME_TYPE,
														  KEY_PLAYERS	};
	
	// DB info: it's name, and the table we are using (just one).
	public static final String DATABASE_NAME = "MyDb";
	public static final String DATABASE_TABLE = "mainTable";
	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 5;
	
	private static final String DATABASE_CREATE_SQL = "create table " + DATABASE_TABLE 
													+ " (" + KEY_ROWID + " integer primary key autoincrement, "
			
			// CHANGE 2:
			 
			// TODO: Place your fields here!
			// + KEY_{...} + " {type} not null"
			//	- Key is the column name you created above.
			//	- {type} is one of: text, integer, real, blob
			//		(http://www.sqlite.org/datatype3.html)
			//  - "not null" means it is a required field (must be given a value).
			// NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
			+ KEY_LATITUDE + " blob not null, "
			+ KEY_LONGITUDE + " blob not null, "
			+ KEY_DATE + " text not null, "
			+ KEY_TIME + " text not null, "
			+ KEY_MIN_AGE + " text not null, "
		   	+ KEY_MAX_AGE + " text not null, "
		    + KEY_SKILL_LEVEL + " int not null, "
		    + KEY_GENDER + " text not null, "
		    + KEY_PITCH + " text not null, "
		    + KEY_GAME_TYPE + " text not null, "
		    + KEY_PLAYERS + " text not null "
			
			// Rest  of creation:
			+ ");";
	
			
	// Context of application who uses us.
	private final Context context;
	
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	/////////////////////////////////////////////////////////////////////
	//	Public methods:
	/////////////////////////////////////////////////////////////////////
	
	public DBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	// Open the database connection.
	public DBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}
	
	// Add a new set of values to the database.
	public long insertRow(double latitude, double longitude, String date, String time, String minAge, String maxAge, int skillLevel, String gender, String pitch, String gameType, int players){
		 //
		 //CHANGE 3:
		 //		
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_LATITUDE, latitude);
		initialValues.put(KEY_LONGITUDE, longitude);
		initialValues.put(KEY_DATE, date);
		initialValues.put(KEY_TIME, time);
		initialValues.put(KEY_MIN_AGE, minAge);
		initialValues.put(KEY_MAX_AGE, maxAge);
		initialValues.put(KEY_SKILL_LEVEL, skillLevel);
		initialValues.put(KEY_GENDER, gender);
		initialValues.put(KEY_PITCH, pitch);
		initialValues.put(KEY_GAME_TYPE, gameType);
		initialValues.put(KEY_PLAYERS, players);
		
		
		// Insert it into the database.
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE, where, null) != 0;
	}
	
	public void deleteAll() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));				
			} while (c.moveToNext());
		}
		c.close();
	}
	
	// Return all data in the database.
	public Cursor getAllRows() {
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
							where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId)
	public Cursor getRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Change an existing row to be equal to new data.
	public boolean updateRow(long rowId, double latitude, double longitude, String date, String time, String minAge, String maxAge, int skillLevel, String gender, String pitch, String gameType, int players) {

		String where = KEY_ROWID + "=" + rowId;

		
		// CHANGE 4:
		//
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_LATITUDE, latitude);
		newValues.put(KEY_LONGITUDE, longitude);
		newValues.put(KEY_DATE, date);
		newValues.put(KEY_TIME, time);
		newValues.put(KEY_MIN_AGE, minAge);
		newValues.put(KEY_MAX_AGE, maxAge);
		newValues.put(KEY_SKILL_LEVEL, skillLevel);
		newValues.put(KEY_GENDER, gender);
		newValues.put(KEY_PITCH, pitch);
		newValues.put(KEY_GAME_TYPE, gameType);
		newValues.put(KEY_PLAYERS, players);
		
		
		// Insert it into the database.
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}
	
	
	
	/////////////////////////////////////////////////////////////////////
	//	Private Helper Classes:
	/////////////////////////////////////////////////////////////////////
	
	/**
	 * Private class which handles database creation and upgrading.
	 * Used to handle low-level database access.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);			
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			
			// Recreate new database:
			onCreate(_db);
		}
	}
}

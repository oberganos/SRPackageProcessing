package oberga2.illinois.edu.srpackageprocessing.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * The purpose of this class is to contain all of the helper functions necessary to perform SQLite
 * database operations.
 *
 * Currently, adding a row to the database and getting all database information are implemented.
 */
public class ProcessorHelper extends SQLiteOpenHelper {

    //used for logs
    private static final String TAG = "ProcessorHelper";

    //must increment database version every time a change is made to the database!
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = ProcessorContract.Packages.TABLE_NAME;

    /**
     * Constructor for the ProcessorHelper.
     * The location of the database relies on the Context given.
     *
     * @param context used for locating path to the database
     */
    public ProcessorHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProcessorContract.Packages.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Currently, every time the version is updated, the whole table is dropped and recreated
        //I have to modify this soon so that old data is transferred into the new database
        db.execSQL("DROP TABLE IF EXISTS " + ProcessorContract.Packages.TABLE_NAME);
        onCreate(db);
    }

    /**
     * The purpose of this function is to add an entry of data(package) into the database.
     *
     * @param recipient the recipient of the package
     * @param firm where the package is from
     * @param date date the package was processed
     * @param count number of packages processed
     * @param notes any additional information
     * @return TRUE if package was added to the database, FALSE if an error occurred
     */
    public boolean addData(String recipient, String firm, String date, String count, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //add the packages information into the content values
        contentValues.put(ProcessorContract.Packages.COLUMN_RECIPIENT_NAME, recipient);
        contentValues.put(ProcessorContract.Packages.COLUMN_FIRM, firm);
        contentValues.put(ProcessorContract.Packages.COLUMN_ENTRY_DATE, date);
        contentValues.put(ProcessorContract.Packages.COLUMN_COUNT, count);
        contentValues.put(ProcessorContract.Packages.COLUMN_NOTES, notes);

        //since the package is being processed, the pickup information will be blank
        contentValues.put(ProcessorContract.Packages.COLUMN_PICKUP_DATE, "");
        contentValues.put(ProcessorContract.Packages.COLUMN_PICKUP_NAME, "");
        contentValues.put(ProcessorContract.Packages.COLUMN_PICKUP_ID, "");

        //attempt to add the package to the database
        Log.v(TAG, "addData: Attempting to add data to database");
        long result = db.insert(DATABASE_NAME, null, contentValues);

        //return whether the database insert was successful
        if(result == -1) {
            //an error occurred in the database insert
            return false;
        } else {
            //data was inserted into database successfully
            return true;
        }
    }

    /**
     * Get all of the data in the database.
     *
     * @return a Cursor containing all database data
     */
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + ProcessorContract.Packages.TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}

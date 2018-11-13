package oberga2.illinois.edu.srpackageprocessing.database;

import android.provider.BaseColumns;

/**
 * This class is a contract class that explicitly specifies the layout of the schema for this
 * database, or a formal declaration of how a database is organized.
 *
 * This class isn't meant to be instantiated
 */
public final class ProcessorContract {
    /**
     * This function is the constructor for the ProcessorContract class. Since this class should
     * never be instantiated, the constructor is private.
     */
    private ProcessorContract() {

    }

    /**
     * This class defines the table contents for the packages database.
     *
     * The variables in this class represent all of the information necessary to complete processing
     * and checkout of packages in the Shipping and Receiving office at the Mechanical Engineering
     * Department of UIUC.
     */
    public static class Packages implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_RECIPIENT_NAME = "recipient";
        public static final String COLUMN_ENTRY_DATE = "date";
        public static final String COLUMN_FIRM = "firm";
        public static final String COLUMN_COUNT = "count";
        public static final String COLUMN_PICKUP_NAME = "pickup_name";
        public static final String COLUMN_PICKUP_ID = "pickup_id";
        public static final String COLUMN_PICKUP_DATE = "pickup_date";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RECIPIENT_NAME + " TEXT, " +
                COLUMN_ENTRY_DATE + " INTEGER, " +
                COLUMN_FIRM + " TEXT, " +
                COLUMN_COUNT + " INTEGER" +
                COLUMN_PICKUP_NAME + " TEXT, " +
                COLUMN_PICKUP_ID + " INTEGER, " +
                COLUMN_PICKUP_DATE + " INTEGER, " +  ")";

        private static final String DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + Packages.TABLE_NAME;
    }
}

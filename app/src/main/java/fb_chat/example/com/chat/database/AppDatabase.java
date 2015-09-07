package fb_chat.example.com.chat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AppDatabase extends SQLiteOpenHelper {

    private static final String TAG = AppDatabase.class.getCanonicalName();

    public static final String DATABASE_NAME = "fbchat.sqlite";
    public static final int DATABASE_VERSION = 1;

    public interface Tables {
        String CHAT = "chat";
    }

    public AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CHAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade() from " + oldVersion + " to " + newVersion);

        if (oldVersion != newVersion) {
            throw new IllegalStateException("error upgrading the database to version "
                + newVersion);
        }
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

    String CREATE_TABLE_CHAT = "CREATE TABLE IF NOT EXISTS " + Tables.CHAT + " (" +
        AppContract.Chats.ID + " INTEGER PRIMARY KEY, " +
        AppContract.Chats.KEY + " VARCHAR NOT NULL, " +
        AppContract.Chats.MESSAGE + " VARCHAR, " +
        AppContract.Chats.USERNAME + " VARCHAR, " +
        " UNIQUE (" + AppContract.Chats.KEY + ") ON CONFLICT REPLACE" +
        ")";
}
        

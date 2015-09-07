package fb_chat.example.com.chat.database;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.common.base.Strings;

import java.util.ArrayList;

public class AppProvider extends ContentProvider {

    private static final String TAG = AppProvider.class.getCanonicalName();
    private AppDatabase mOpenHelper;
    private static UriMatcher sUriMatcher = AppUriMatcher.sUriMatcher;

    @Override
    public boolean onCreate() {
        mOpenHelper = new AppDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        // Most cases are handled with simple SelectionBuilder
        final SelectionBuilder builder = buildSimpleSelection(uri);
        Log.w("provider", builder.toString());
        Cursor cursor = builder.where(selection, selectionArgs).query(db, projection, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case AppUriMatcher.CHATS:
                return AppContract.Chats.CONTENT_TYPE;
            case AppUriMatcher.CHAT_ID:
                return AppContract.Chats.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //Log.w(TAG, "insert(uri=" + uri + ", values=" + values.toString() + ")");
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        boolean syncToNetwork = !AppContract.hasCallerIsSyncAdapterParameter(uri);
        switch (match) {
            case AppUriMatcher.CHATS:
                long chatId = db.insertOrThrow(AppDatabase.Tables.CHAT, null, values);
                notifyChange(uri, syncToNetwork);
                notifyChange(AppContract.Chats.CONTENT_URI, syncToNetwork);
                return AppContract.Chats.buildChatUri(String.valueOf(chatId));
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.w(TAG, "delete(uri=" + uri + ")");
        if (uri.equals(AppContract.BASE_CONTENT_URI)) {
            // Handle whole database deletes (e.g. when signing out)
            deleteDatabase();
            getContext().getContentResolver().notifyChange(uri, null, false);
            return 1;
        }
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSimpleSelection(uri);
        int retVal = builder.where(selection, selectionArgs).delete(db);
        getContext().getContentResolver().notifyChange(uri, null,
            !AppContract.hasCallerIsSyncAdapterParameter(uri));
        return retVal;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.w(TAG, "update(uri=" + uri + ", values=" + values.toString() + ")");
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSimpleSelection(uri);
        int retVal = builder.where(selection, selectionArgs).update(db, values);
        boolean syncToNetwork = !AppContract.hasCallerIsSyncAdapterParameter(uri);
        getContext().getContentResolver().notifyChange(uri, null, syncToNetwork);
        return retVal;
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
        throws OperationApplicationException {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        if (!Strings.isNullOrEmpty(method)) {
            final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
            switch (method) {
                default:

                    break;
            }
        }
        return super.call(method, arg, extras);
    }

    private void notifyChange(Uri uri, boolean syncToNetwork) {
        getContext().getContentResolver().notifyChange(uri, null, syncToNetwork);
    }

    private void deleteDatabase() {
        mOpenHelper.close();
        Context context = getContext();
        AppDatabase.deleteDatabase(context);
        mOpenHelper = new AppDatabase(context);
    }

    private SelectionBuilder buildSimpleSelection(Uri uri) {
        final SelectionBuilder builder = new SelectionBuilder();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case AppUriMatcher.CHATS:
                return builder.table(AppDatabase.Tables.CHAT);
            case AppUriMatcher.CHAT_ID:
                final String chatId = AppContract.Chats.getChatId(uri);
                return builder.table(AppDatabase.Tables.CHAT).where(AppContract.Chats.ID + "=?", chatId);
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }
}

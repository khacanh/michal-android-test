package fb_chat.example.com.chat.database;

import android.database.Cursor;

public class CursorHelper {

    public static Long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }

    public static Float getFloat(Cursor cursor, String columnName) {
        return cursor.getFloat(cursor.getColumnIndexOrThrow(columnName));
    }

    public static Integer getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

    public static String getString(Cursor cursor, String columnName) {
        final int columnIndex = cursor.getColumnIndexOrThrow(columnName);
        if (cursor.isNull(columnIndex)) {
            return null;
        } else {
            return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
        }
    }

    public static String getString(Cursor cursor, String columnName, String alternate) {
        final int columnIndex = cursor.getColumnIndexOrThrow(columnName);
        if (cursor.isNull(columnIndex)) {
            return alternate;
        } else {
            return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
        }
    }

    public static Boolean getBoolean(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName)) == 1;
    }

    public static Double getDouble(Cursor cursor, String columnName) {
        final int columnIndex = cursor.getColumnIndexOrThrow(columnName);
        if (cursor.isNull(columnIndex)) {
            return null;
        } else {
            return cursor.getDouble(columnIndex);
        }
    }

    public static boolean isNull(Cursor cursor, String columnName) {
        return cursor.isNull(cursor.getColumnIndexOrThrow(columnName));
    }


    public static boolean isEmpty(Cursor cursor) {
        return cursor == null || cursor.getCount() == 0;
    }

    public static int sumIntColumn(Cursor cursor, String columnName) {
        int value = 0;
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToPrevious();
            int columnIndex = cursor.getColumnIndexOrThrow(columnName);
            while (cursor.moveToNext()) {
                value += cursor.getInt(columnIndex);
            }
        }
        return value;
    }

    public static double sumDoubleColumn(Cursor cursor, String columnName) {
        double value = 0;
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToPrevious();
            int columnIndex = cursor.getColumnIndexOrThrow(columnName);
            while (cursor.moveToNext()) {
                value += cursor.getDouble(columnIndex);
            }
        }
        return value;
    }
}

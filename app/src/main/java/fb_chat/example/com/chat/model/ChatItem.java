package fb_chat.example.com.chat.model;

import android.content.ContentValues;
import android.database.Cursor;

import org.chalup.microorm.MicroOrm;
import org.chalup.microorm.annotations.Column;

import fb_chat.example.com.chat.database.AppContract;

public class ChatItem {

    static MicroOrm ORM = new MicroOrm();
    @Column(AppContract.ChatColumns.ID)
    Integer id;
    @Column(AppContract.ChatColumns.KEY)
    String key;
    @Column(AppContract.ChatColumns.MESSAGE)
    String message;
    @Column(AppContract.ChatColumns.USERNAME)
    String userName;

    public ChatItem() {
    }

    public ChatItem(String key, String message, String userName) {
        this.key = key;
        this.message = message;
        this.userName = userName;
    }

    public ChatItem(Cursor cursor) {
        fromCursor(cursor);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String id) {
        this.key = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ContentValues toContextValues() {
        return ORM.toContentValues(this);
    }

    public void fromCursor(Cursor cursor) {
        ORM.fromCursor(cursor, this);
    }
}

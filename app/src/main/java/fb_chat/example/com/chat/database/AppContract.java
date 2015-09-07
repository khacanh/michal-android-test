package fb_chat.example.com.chat.database;

import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

public class AppContract {

    public static final String AUTHORITY = "fb_chat.example.com.chat.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public interface Paths {
        String PATH_CHAT = "chat";
    }

    public interface ChatColumns {
        String ID = "_id";
        String KEY = "key";
        String MESSAGE = "message";
        String USERNAME = "userName";
    }


    public static class Chats implements ChatColumns {
        public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(Paths.PATH_CHAT).build();
        public static final String CONTENT_TYPE =
            "vnd.android.cursor.dir/vnd.fb_chat.chat";
        public static final String CONTENT_ITEM_TYPE =
            "vnd.android.cursor.item/vnd.fb_chat.chat";

        public static Uri buildChatUri(String chatId) {
            return CONTENT_URI.buildUpon().appendPath(chatId).build();
        }

        public static String getChatId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static boolean hasCallerIsSyncAdapterParameter(Uri uri) {
        return TextUtils.equals("true",
            uri.getQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER));
    }

    public static Uri addCallerIsSyncAdapterParameter(Uri uri) {
        return uri.buildUpon().appendQueryParameter(
            ContactsContract.CALLER_IS_SYNCADAPTER, "true").build();
    }
}

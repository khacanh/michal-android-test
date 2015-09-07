package fb_chat.example.com.chat.database;

import android.content.UriMatcher;
import android.text.TextUtils;

public class AppUriMatcher {

    public static final String PATH_SEPARATOR = "/";
    private static final String NUMBER = "#";
    private static final String WILDCARD = "*";

    public static final int CHATS = 100;
    public static final int CHAT_ID = 101;

    public static UriMatcher sUriMatcher = buildUriMatcher();

    private static void addEndpoint(UriMatcher matcher, int code, String... pathSegments) {
        matcher.addURI(AppContract.AUTHORITY, TextUtils.join(PATH_SEPARATOR, pathSegments), code);
    }

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        addEndpoint(matcher, CHATS, AppContract.Paths.PATH_CHAT);
        addEndpoint(matcher, CHAT_ID, AppContract.Paths.PATH_CHAT, NUMBER);

        return matcher;
    }
}

package fb_chat.example.com.chat.model;

public class ChatItem {

    String key;
    String message;
    String userName;

    public ChatItem() {
    }

    public ChatItem(String key, String message, String userName) {
        this.key = key;
        this.message = message;
        this.userName = userName;
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
}

package fb_chat.example.com.chat.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.common.base.Strings;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fb_chat.example.com.chat.R;
import fb_chat.example.com.chat.model.ChatItem;
import fb_chat.example.com.chat.ui.adapter.ChatAdapter;
import fb_chat.example.com.chat.view.DividerItemDecoration;

public class MainActivity extends AppCompatActivity {

    static final String USERNAME = "mike";
    @Bind(R.id.list) RecyclerView mList;
    @Bind(R.id.main_message) EditText mMessage;
    @Bind(R.id.main_submit) Button mSubmit;
    @OnClick(R.id.main_submit) void add() {
        mSubmit.setEnabled(false);
        String message = mMessage.getText().toString();
        if (!Strings.isNullOrEmpty(message)) {
            /*mChat.add(new ChatItem(message, null));
            mAdapter.notifyDataSetChanged();*/
            mMessage.setText(null);
            if (chatDB != null) {
                ChatItem newItem = new ChatItem(null, message, USERNAME);
                chatDB.push().setValue(newItem);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Message cannot be empty!", Toast.LENGTH_LONG).show();
        }
        mSubmit.setEnabled(true);
    }
    ArrayList<ChatItem> mChat = new ArrayList<>();
    ChatAdapter mAdapter;
    Firebase chatDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
        chatDB = new Firebase("https://mysquar-test.firebaseio.com/chat");
        chatDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                if (snapshot != null) {
                    ChatItem newItem = snapshot.getValue(ChatItem.class);
                    mChat.add(newItem);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        LinearLayoutManager listManager = new LinearLayoutManager(this);
        mList.setLayoutManager(listManager);
        DividerItemDecoration deco = new DividerItemDecoration(1, Color.GRAY);
        mList.addItemDecoration(deco);
        mAdapter = new ChatAdapter(this, mChat);
        mList.setAdapter(mAdapter);
    }
}

package com.example.tinderclone.Chats;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.tinderclone.Matches.MatchesAdapter;
import com.example.tinderclone.Matches.MatchesObject;
import com.example.tinderclone.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mChatAdapter;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String curntUserId, matchId, chatId;
    TextView tvName;
    EditText mSendEditText;
    ImageButton mSendBtn;
    DatabaseReference mDatabaseUser, mDatabaseChat, mDatabaseImgUrl;
    MaterialToolbar toolbar;
    LinearLayoutManager linearLayoutManager;
    //String userImgUrl, otherUserImageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        curntUserId = user.getUid();

        mSendEditText = findViewById(R.id.messageText);
        mSendBtn = findViewById(R.id.sendBtn);
        tvName = findViewById(R.id.tvName);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("matchName");
        matchId = intent.getExtras().getString("matchId");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        tvName.setText(name);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(curntUserId)
                .child("Connections")
                .child("Matches")
                .child(matchId)
                .child("ChatId");

        mDatabaseChat = FirebaseDatabase.getInstance().getReference().child("Chat");

        mDatabaseImgUrl = FirebaseDatabase.getInstance().getReference().child("Users");


        getChatId();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);

        mChatAdapter = new ChatsAdapter(getDataSetChats(), ChatActivity.this);
        Context context;
        linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mChatAdapter);

        mRecyclerView.smoothScrollToPosition(mChatAdapter.getItemCount());

        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String sendMsg = mSendEditText.getText().toString();
        if (!sendMsg.isEmpty()) {
            DatabaseReference newMsgDb = mDatabaseChat.push();
            Map newMsg = new HashMap();
            newMsg.put("createdByUser", curntUserId);
            newMsg.put("text", sendMsg);
            newMsgDb.setValue(newMsg);
        }
        mSendEditText.setText(null);
        linearLayoutManager.setStackFromEnd(true);


    }

    private void getChatId() {
        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    chatId = snapshot.getValue().toString();
                    mDatabaseChat = mDatabaseChat.child(chatId);
                    getChatMessages();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getChatMessages() {
        mDatabaseChat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()) {
                    String message = null;
                    String createdByUser = null;
                    if (snapshot.child("text").getValue() != null) {
                        message = snapshot.child("text").getValue().toString();
                    }
                    if (snapshot.child("createdByUser").getValue() != null) {
                        createdByUser = snapshot.child("createdByUser").getValue().toString();
                    }
                    if (message != null && createdByUser != null) {
                        Boolean currentUserBoolean = false;
                        if (createdByUser.equals(curntUserId)) {
                            currentUserBoolean = true;
                        }
                        ChatsObject newMsg = new ChatsObject(message, currentUserBoolean);
                        resultChats.add(newMsg);
                        mChatAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private ArrayList<ChatsObject> resultChats = new ArrayList<ChatsObject>();

    private List<ChatsObject> getDataSetChats() {
        return resultChats;
    }

}
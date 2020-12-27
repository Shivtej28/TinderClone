package com.example.tinderclone.Matches;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.os.Bundle;

import com.example.tinderclone.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    Adapter mMatchesAdapter;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String curntUserId;
    MaterialToolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        curntUserId = user.getUid();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);

        mMatchesAdapter = new MatchesAdapter(getDataSetMatches(), this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mMatchesAdapter);
        getUserMatchId();
    }

    private void getUserMatchId() {
        DatabaseReference matchesDb = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(curntUserId)
                .child("Connections")
                .child("Matches");
        matchesDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot match: snapshot.getChildren()){
                        FetchMatchInformation(match.getKey());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void FetchMatchInformation(String key) {
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(key);
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String userId = snapshot.getKey();
                    String userName = "";
                    String userProfImgUrl = "";
                    if(snapshot.child("Name").getValue() != null){
                        userName = snapshot.child("Name").getValue().toString();
                    }
                    if(snapshot.child("profImageUrl").getValue() != null){
                        userProfImgUrl = snapshot.child("profImageUrl").getValue().toString();
                    }
                    MatchesObject obj = new MatchesObject(userId,userName, userProfImgUrl);
                    resultMatches.add(obj);
                    mMatchesAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private ArrayList<MatchesObject> resultMatches = new ArrayList();
    private List<MatchesObject> getDataSetMatches() {
        return resultMatches;
    }
}
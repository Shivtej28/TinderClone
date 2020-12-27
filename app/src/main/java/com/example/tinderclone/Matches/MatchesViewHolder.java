package com.example.tinderclone.Matches;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tinderclone.Chats.ChatActivity;
import com.example.tinderclone.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchesViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mMatchName;
    public CircleImageView profMatch;
    public TextView userId;

    public MatchesViewHolder(@NonNull View itemView) {
        super(itemView);
        mMatchName = itemView.findViewById(R.id.matchName);
        profMatch = itemView.findViewById(R.id.profMatch);
        userId = itemView.findViewById(R.id.userId);
        itemView.setOnClickListener(this);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                intent.putExtra("matchId", userId.getText().toString());
                intent.putExtra("matchName", mMatchName.getText().toString());

                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ChatActivity.class);
        Bundle b = new Bundle();
        b.putString("matchId",userId.getText().toString() );
        b.putString("matchName", mMatchName.getText().toString());
        intent.putExtras(b);
        v.getContext().startActivity(intent);
    }
}

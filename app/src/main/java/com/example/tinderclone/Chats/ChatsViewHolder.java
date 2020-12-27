package com.example.tinderclone.Chats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tinderclone.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mMessage;
    public LinearLayout mContainer;
    public CircleImageView ivProf;



    public ChatsViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMessage = itemView.findViewById(R.id.message);
        mContainer = itemView.findViewById(R.id.container);
        //ivProf = itemView.findViewById(R.id.ivProf);


    }

    @Override
    public void onClick(View v) {
    }

}

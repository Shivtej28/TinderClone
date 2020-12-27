package com.example.tinderclone.Chats;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tinderclone.R;

import java.util.List;

import static com.example.tinderclone.R.drawable.btn_background;
import static com.example.tinderclone.R.drawable.gradient;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsViewHolder> {

    private List<ChatsObject> chatList;
    private Context context;

    public ChatsAdapter(List<ChatsObject> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;

    }

    @NonNull
    @Override
    public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatt, null, false);
        ChatsViewHolder rcv = new ChatsViewHolder(layoutView);

        return rcv;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ChatsViewHolder holder, int position) {
        holder.mMessage.setText(chatList.get(position).getMessage());
        Log.i("Current Boolean", chatList.get(position).getCurrentUser().toString());

        if (chatList.get(position).getCurrentUser()) {
            holder.mContainer.setGravity(Gravity.END);
            //holder.mContainer.setGravity(Gravity.END);
            holder.mMessage.setTextColor(Color.WHITE);
            holder.mMessage.setBackground(ContextCompat.getDrawable(context, R.drawable.msg_background));
            //holder.mContainer.setBackgroundColor(Color.parseColor("#F4F4F4"));
        } else {
            holder.mContainer.setGravity(Gravity.START);
            holder.mMessage.setTextColor(Color.BLACK);
            holder.mMessage.setBackground(ContextCompat.getDrawable(context, R.drawable.background_msg_other));


            //holder.mContainer.setBackgroundColor(Color.parseColor("#2DB4C8"));
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
}

package com.example.tinderclone.Matches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tinderclone.R;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesViewHolder>  {

    private List<MatchesObject> matchesList;
    private Context context;

    public MatchesAdapter(List<MatchesObject> matchesList, Context context){
        this.matchesList = matchesList;
        this.context = context;

    }
    @NonNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches, null, false);
        MatchesViewHolder rcv = new MatchesViewHolder(layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolder holder, int position) {
        holder.mMatchName.setText(matchesList.get(position).getuserName());
        holder.userId.setText(matchesList.get(position).getUserId());
        if(matchesList.get(position).getProfImgUrl().equals("")){
            holder.profMatch.setImageResource(R.drawable.tinder);
        }else {
            Glide.with(context).load(matchesList.get(position).getProfImgUrl()).into(holder.profMatch);
        }

    }

    @Override
    public int getItemCount() {
        return matchesList.size();
    }
}

package com.example.tinderclone.Cards;

import android.content.Context;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tinderclone.Cards.Cards;
import com.example.tinderclone.R;

public class arrayAdapter extends ArrayAdapter<Cards> {
    Context context;
    public arrayAdapter(Context context, int resourceId, List<Cards> items){
        super(context, resourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Cards card_item = getItem(position);
        View currentItemView = convertView;
        if(currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);

        }

        ImageView imageView = currentItemView.findViewById(R.id.imageView);
        TextView name = currentItemView.findViewById(R.id.name);

        name.setText(card_item.getName());
        name.bringToFront();
        if(card_item.getProfImgUrl() == null){
            imageView.setImageResource(R.drawable.tinderlogo2);
        }else {
            Glide.with(getContext()).load(card_item.getProfImgUrl()).into(imageView);
        }

        return currentItemView;


    }


}

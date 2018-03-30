package com.teamnamenotfoundexception.hoteller.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teamnamenotfoundexception.hoteller.Activities.MainActivity;
import com.teamnamenotfoundexception.hoteller.Database.Restaurant;
import com.teamnamenotfoundexception.hoteller.R;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private List<Restaurant> restItems;
    private Context context;

    public RestaurantAdapter(Context context, List<Restaurant> restItems) {
        this.context = context;
        this.restItems = restItems;
    }

    public void setData(List<Restaurant> list) {
        restItems = new ArrayList<>();
        restItems.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restentry, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RestaurantAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final Restaurant rest = restItems.get(position);
        holder.restName.setText(rest.getRestName());
        holder.restArea.setText(rest.getRestArea());
        Glide.with(context).load(rest.mImagePath).into(holder.restImage);
    }

    @Override
    public int getItemCount() {
        return restItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView restImage;
        public TextView restName, restArea;

        public ViewHolder(final View item) {
            super(item);
            restImage = item.findViewById(R.id.rest_image);
            restName = item.findViewById(R.id.restName);
            restArea = item.findViewById(R.id.restArea);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            MainActivity.displayItems(restItems.get(getLayoutPosition()).getRestId(), context);
        }
    }
}

package com.ibm.ibm_coding_challenge.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ibm.ibm_coding_challenge.databinding.ItemLocationBinding;
import com.ibm.ibm_coding_challenge.model.SearchResult;
import com.ibm.ibm_coding_challenge.ui.WeatherDetailsActivity;
import com.ibm.ibm_coding_challenge.utils.Constants;

import java.util.ArrayList;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.LocationListViewHolder> {
    Context context;
    ArrayList<SearchResult> locations;

    public LocationListAdapter(Context context, ArrayList<SearchResult> locations) {
        this.context = context;
        this.locations = locations;
    }

    @NonNull
    @Override
    public LocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLocationBinding binding = ItemLocationBinding.inflate(LayoutInflater.from(context), parent, false);
        return new LocationListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationListViewHolder holder, int position) {
        SearchResult result = locations.get(position);
        holder.title.setText(result.getTitle());
        holder.type.setText(result.getLocation_type());
        holder.id.setText(result.getWoeid());

        //can be done with itemtouchlistener also
        holder.card.setOnClickListener(view -> {
            context.startActivity(new Intent(context, WeatherDetailsActivity.class).putExtra(Constants.WOEID, result.getWoeid()));
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    class LocationListViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView id;
        TextView type;
        CardView card;

        public LocationListViewHolder(@NonNull ItemLocationBinding itemBinding) {
            super(itemBinding.getRoot());
            title = itemBinding.tvTitle;
            id = itemBinding.tvId;
            type = itemBinding.tvType;
            card = itemBinding.cardLocations;
        }
    }
}

package com.ibm.ibm_coding_challenge.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibm.ibm_coding_challenge.databinding.ItemSearchBinding;
import com.ibm.ibm_coding_challenge.model.db.LocationEntity;
import com.ibm.ibm_coding_challenge.utils.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SearchQueriesAdapter extends RecyclerView.Adapter<SearchQueriesAdapter.SearchResultViewHolder> {
    Context context;
    ArrayList<LocationEntity> searchQueries;

    public SearchQueriesAdapter(Context context, ArrayList<LocationEntity> searchQueries) {
        this.context = context;
        this.searchQueries = searchQueries;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchBinding binding = ItemSearchBinding.inflate(LayoutInflater.from(context), parent, false);
        return new SearchResultViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        LocationEntity result = searchQueries.get(position);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        //given more time, we can remove all the strings and use them from strings.xml
        if (result.getQuery().equals(Constants.EMPTY)){
            holder.title.setText(result.getTitle());
        }else{
            holder.title.setText(result.getQuery());
        }

        holder.timestamp.setText(dateFormat.format(result.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return searchQueries.size();
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView timestamp;

        public SearchResultViewHolder(@NonNull ItemSearchBinding itemBinding) {
            super(itemBinding.getRoot());
            title = itemBinding.tvSearchTitle;
            timestamp = itemBinding.tvSearchTimestamp;
        }
    }
}

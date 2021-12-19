package com.ibm.ibm_coding_challenge.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ibm.ibm_coding_challenge.R;
import com.ibm.ibm_coding_challenge.databinding.ItemLocationBinding;
import com.ibm.ibm_coding_challenge.databinding.ItemWeatherDetailBinding;
import com.ibm.ibm_coding_challenge.model.SearchResult;
import com.ibm.ibm_coding_challenge.model.WeatherDetail;
import com.ibm.ibm_coding_challenge.ui.WeatherDetailsActivity;
import com.ibm.ibm_coding_challenge.utils.Constants;

import java.util.ArrayList;

public class WeatherDetailsAdapter extends RecyclerView.Adapter<WeatherDetailsAdapter.WeatherDetailViewHolder> {
    Context context;
    ArrayList<WeatherDetail> weatherDetails;

    public WeatherDetailsAdapter(Context context, ArrayList<WeatherDetail> weatherDetails) {
        this.context = context;
        this.weatherDetails = weatherDetails;
    }

    @NonNull
    @Override
    public WeatherDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWeatherDetailBinding binding = ItemWeatherDetailBinding.inflate(LayoutInflater.from(context), parent, false);
        return new WeatherDetailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDetailViewHolder holder, int position) {
        WeatherDetail result = weatherDetails.get(position);

        //given more time, we can remove all the strings and use them from strings.xml
        holder.date.setText(result.getApplicable_date());
        holder.minTemp.setText("Min Temp: " + context.getResources().getString(R.string.temperature, result.getMin_temp()));
        holder.max_temp.setText("Max Temp: " + context.getResources().getString(R.string.temperature, result.getMax_temp()));
        holder.avgTemp.setText("Avg Temp: " + context.getResources().getString(R.string.temperature, result.getThe_temp()));
        holder.humidity.setText("Hmidity: " + result.getHumidity());
        holder.weatherState.setText("Weather State: " + result.getWeather_state_name());
        holder.airPressure.setText("Air Pressure: " + result.getAir_pressure());

        Glide.with(context)
                .load(Constants.IMAGE_URL + result.getWeather_state_abbr() + ".png")
                .placeholder(R.drawable.ic_placeholder)
                .into(holder.state);
    }

    @Override
    public int getItemCount() {
        return weatherDetails.size();
    }

    class WeatherDetailViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView minTemp;
        TextView max_temp;
        TextView avgTemp;
        TextView humidity;
        TextView airPressure;
        TextView weatherState;
        ImageView state;

        public WeatherDetailViewHolder(@NonNull ItemWeatherDetailBinding itemBinding) {
            super(itemBinding.getRoot());
            date = itemBinding.tvDate;
            minTemp = itemBinding.tvMinTemp;
            max_temp = itemBinding.tvMaxTemp;
            avgTemp = itemBinding.tvAvgTemp;
            humidity = itemBinding.tvHumidity;
            airPressure = itemBinding.tvAirPressure;
            weatherState = itemBinding.tvWeatherState;
            state = itemBinding.imgWeatherCondition;
        }
    }
}

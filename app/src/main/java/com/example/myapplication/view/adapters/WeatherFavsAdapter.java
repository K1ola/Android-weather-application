package com.example.myapplication.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FavTownItemBinding;
import com.example.myapplication.model.Weather;

import java.util.LinkedList;
import java.util.List;

public class WeatherFavsAdapter extends RecyclerView.Adapter<WeatherFavsAdapter.WeatherHolder> {
    private List<Weather> items = new LinkedList<>();

    public void setData(List<Weather> data) {
        items.clear();
        items.addAll(data);
    }

    @NonNull
    @Override
    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FavTownItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.fav_town_item, parent, false);
        return new WeatherHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


//    Holder
    class WeatherHolder extends RecyclerView.ViewHolder {
        FavTownItemBinding binding;

        public WeatherHolder(FavTownItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Weather weather) {
            binding.setWeather(weather);
            binding.executePendingBindings();
        }
    }
}

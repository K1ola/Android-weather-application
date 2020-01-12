package com.example.myapplication.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FoundTownItemBinding;
import com.example.myapplication.model.Weather;
import com.example.myapplication.viewModel.DataViewModel;

import java.util.LinkedList;
import java.util.List;

public class FoundTownsAdapter extends RecyclerView.Adapter<FoundTownsAdapter.FoundTownsHolder> {
    private List<Weather> items = new LinkedList<>();
    private DataViewModel dataViewModel;

    public void setData(List<Weather> data, DataViewModel dataViewModel) {
        this.dataViewModel = dataViewModel;
        items.clear();
        items.addAll(data);
    }

    @NonNull
    @Override
    public FoundTownsAdapter.FoundTownsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FoundTownItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.found_town_item, parent, false);

        return new FoundTownsAdapter.FoundTownsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoundTownsAdapter.FoundTownsHolder holder, int position) {
        holder.bind(items.get(position), dataViewModel);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    //    Holder
    class FoundTownsHolder extends RecyclerView.ViewHolder {
        FoundTownItemBinding binding;

        public FoundTownsHolder(FoundTownItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Weather weather, DataViewModel dataViewModel) {
            binding.setDataViewModel(dataViewModel);
            binding.setWeather(weather);
            binding.executePendingBindings();
        }
    }
}
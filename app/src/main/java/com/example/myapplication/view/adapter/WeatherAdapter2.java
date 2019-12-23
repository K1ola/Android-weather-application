package com.example.myapplication.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.BR;
import com.example.myapplication.databinding.HolderItem2Binding;
import com.example.myapplication.model.HolderItem;
import com.example.myapplication.view.callback.ItemClickCallback;
import com.example.myapplication.viewModel.DataViewModel;

import java.util.List;

public class WeatherAdapter2 extends RecyclerView.Adapter<WeatherAdapter2.WeatherHolder2> {
    private DataViewModel dataViewModel;
    private List<HolderItem> items;
    private int layoutId;

    @Nullable
    public ItemClickCallback itemClickCallback;

    public WeatherAdapter2(@LayoutRes int layoutId, DataViewModel data, @Nullable ItemClickCallback itemClickCallback) {
        this.layoutId = layoutId;
        this.dataViewModel = data;
        this.itemClickCallback = itemClickCallback;
    }

    public void setItemClickCallback(ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public void setHolderItems(List<HolderItem> items) {
        this.items = items;
    }

    class WeatherHolder2 extends RecyclerView.ViewHolder {

        final HolderItem2Binding binding;

        public WeatherHolder2(HolderItem2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DataViewModel dataViewModel, Integer position) {
            binding.setViewModel(dataViewModel);

            binding.setVariable(BR.dataViewModel, dataViewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }

    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    @NonNull
    @Override
    public WeatherHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        HolderItem2Binding binding = DataBindingUtil.inflate(inflater, viewType, parent, false);

        binding.setCallback(itemClickCallback);

        return new WeatherHolder2(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHolder2 holder, int position) {
        holder.bind(dataViewModel, position);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

}

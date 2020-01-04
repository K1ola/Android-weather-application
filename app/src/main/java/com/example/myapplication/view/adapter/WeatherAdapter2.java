package com.example.myapplication.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.BR;
import com.example.myapplication.R;
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

        private final TextView mDay;
        private final TextView mTemp;
        private final ImageView mImageView;

        public WeatherHolder2(@NonNull View itemView) {
            super(itemView);
            mDay = itemView.findViewById(R.id.top_text);
            mTemp = itemView.findViewById(R.id.bottom_text);
            mImageView = itemView.findViewById(R.id.image);
        }

        public void bind(DataViewModel dataViewModel, Integer position) {
            mDay.setText(String.valueOf(dataViewModel.get5DaysDataList().get(position).topData));
            mTemp.setText(String.valueOf(dataViewModel.get5DaysDataList().get(position).bottomData));
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
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.holder_item, parent,false);

        return new WeatherHolder2(v);
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

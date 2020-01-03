package com.example.myapplication.view.adapter;

import android.renderscript.Sampler;
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
import com.example.myapplication.model.HolderItem;
import com.example.myapplication.model.Weather;
import com.example.myapplication.view.callback.ItemClickCallback;
import com.example.myapplication.viewModel.DataViewModel;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherHolder> {

    private DataViewModel dataViewModel;
    private List<HolderItem> items;
    private int layoutId;

    @Nullable
    public ItemClickCallback itemClickCallback;

    public WeatherAdapter(@LayoutRes int layoutId, DataViewModel data, @Nullable ItemClickCallback itemClickCallback) {
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

    class WeatherHolder extends RecyclerView.ViewHolder {
        private final TextView mDay;
        private final TextView mTemp;
        private final ImageView mImageView;

        public WeatherHolder(@NonNull View itemView) {
            super(itemView);
            mDay = itemView.findViewById(R.id.top_text);
            mTemp = itemView.findViewById(R.id.bottom_text);
            mImageView = itemView.findViewById(R.id.image);
        }


        public void bind(DataViewModel dataViewModel, int position) {
            mDay.setText(String.valueOf(dataViewModel.get5DaysDataList().get(position).topData));
            mTemp.setText(String.valueOf(dataViewModel.get5DaysDataList().get(position).bottomData));
        }

    }

    public interface OnItemClickListener {
        void onItemClick();
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
    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.holder_item, parent,false);

        return new WeatherHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHolder holder, int position) {
        holder.bind(dataViewModel, position);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

}

package com.example.myapplication.presentation.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;


public final class AdapterWeather extends RecyclerView.Adapter<AdapterWeather.WeatherHolder> {
    private final OnItemClickListener mListener;
    private List<DataSource.DataWeather> mData;

    public AdapterWeather(List<DataSource.DataWeather> data, final OnItemClickListener listener){
        mListener = listener;
        mData = data;
    }

    @NonNull
    @Override
    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.holder_item, parent,false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick();
                }
            }
        });
        return new WeatherHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHolder holder, int position){
        DataSource.DataWeather data = mData.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class WeatherHolder extends RecyclerView.ViewHolder{
        private final TextView mDay;
        private final TextView mTemp;
        private final ImageView mImageView;

        public WeatherHolder(@NonNull View itemView){
            super(itemView);
            mDay = itemView.findViewById(R.id.day);
            mTemp = itemView.findViewById(R.id.temp);
            mImageView = itemView.findViewById(R.id.image);
        }
        public void bind(DataSource.DataWeather ob){
            mDay.setText(ob.mDay);
            mTemp.setText(ob.mTemp);
        }
    }

    public interface OnItemClickListener {
        void onItemClick();
    }
}


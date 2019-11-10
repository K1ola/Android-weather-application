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


public class AdapterWeather extends RecyclerView.Adapter<AdapterWeather.MyViewHolder> {

    MyViewHolder myViewHolder;
    List<DataSource.MyData> mData;

    public AdapterWeather(List<DataSource.MyData> data){
        mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.holder_item, parent,false);
        myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        DataSource.MyData data = mData.get(position);
        holder.mDay.setText(data.mDay);
        holder.mTemp.setText(data.mTemp);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView mDay;
        private final TextView mTemp;
        private final ImageView mImageView;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            mDay = itemView.findViewById(R.id.day);
            mTemp = itemView.findViewById(R.id.temp);
            mImageView = itemView.findViewById(R.id.image);
        }
    }
}


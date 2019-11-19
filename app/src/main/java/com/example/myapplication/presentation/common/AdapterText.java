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


public final class AdapterText extends RecyclerView.Adapter<AdapterText.TextHolder> {
    private List<DataSource.DataWeather> mData;
    public AdapterText(List<DataSource.DataWeather> data){
        mData = data;
    }

    @NonNull
    @Override
    public TextHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.holder_text_item, parent,false);
        return new TextHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TextHolder holder, int position){
        DataSource.DataWeather data = mData.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class TextHolder extends RecyclerView.ViewHolder{
        private final TextView mDay;
        private final TextView mTemp;

        TextHolder(@NonNull View itemView){
            super(itemView);
            mDay = itemView.findViewById(R.id.top_text);
            mTemp = itemView.findViewById(R.id.bottom_text);
        }

        void bind(DataSource.DataWeather ob){
            mDay.setText(ob.mDay);
            mTemp.setText(ob.mTemp);
        }
    }
}


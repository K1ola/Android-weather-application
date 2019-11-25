package com.example.myapplication.presentation.common;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;


public final class AdapterWithText extends RecyclerView.Adapter<AdapterWithText.TextHolder> {
    private List<DataSource.DataText> mData;
    private int dataColor = Color.WHITE;

    public AdapterWithText(List<DataSource.DataText> data){
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
        DataSource.DataText data = mData.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    final class TextHolder extends RecyclerView.ViewHolder{
        private final TextView topData;
        private final TextView bottomData;

        TextHolder(@NonNull View itemView){
            super(itemView);
            topData = itemView.findViewById(R.id.top_text);
            bottomData = itemView.findViewById(R.id.bottom_text);
        }

        void bind(DataSource.DataText ob){
            topData.setText(ob.mTopText);
            topData.setTextColor(dataColor);
            bottomData.setText(ob.mBottomText);
            bottomData.setTextColor(dataColor);
        }
    }

    public void setDataColor(int color) {
        dataColor = color;
    }
}


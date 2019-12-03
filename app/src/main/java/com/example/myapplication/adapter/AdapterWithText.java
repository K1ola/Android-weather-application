package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
//import com.example.myapplication.model.DataSource;
import com.example.myapplication.databinding.HolderTextItemBinding;
import com.example.myapplication.model.DataText;
import com.example.myapplication.viewModel.DataTextViewModel;

import java.util.LinkedList;
import java.util.List;


public final class AdapterWithText extends RecyclerView.Adapter<AdapterWithText.TextHolder> {
//    private List<DataText> mData;
//    private int dataColor = Color.WHITE;
//
//    public AdapterWithText(@NonNull List<DataText> data, int color) {
//        mData = data;
//        dataColor = color;
//    }
//
//    @NonNull
//    @Override
//    public TextHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater
//                .from(parent.getContext())
//                .inflate(R.layout.holder_text_item, parent,false);
//        return new TextHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TextHolder holder, int position){
//        DataText data = mData.get(position);
//        holder.bind(data);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mData.size();
//    }
//
    private DataTextViewModel dataTextViewModel;
    private int layoutId;
    private List<DataText> dataTexts;

    public AdapterWithText(int layoutId, DataTextViewModel viewModel) {
        this.layoutId = layoutId;
        this.dataTextViewModel = viewModel;
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    @Override
    public int getItemCount() {
        return dataTexts == null ? 0 : dataTexts.size();
    }

    public void setData(List<DataText> data) {
        this.dataTexts = data;
    }

    @NonNull
    @Override
    public TextHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        HolderTextItemBinding binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        return new TextHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TextHolder holder, int position) {
        holder.bind(dataTextViewModel, position);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }


    final class TextHolder extends RecyclerView.ViewHolder{
        final HolderTextItemBinding binding;

        public TextHolder(HolderTextItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DataTextViewModel data, Integer position) {
            binding.setDataTextViewModel(data);
            binding.executePendingBindings();
        }
    }
        ////
//        private final TextView topData;
//        private final TextView bottomData;
//
//        TextHolder(@NonNull View itemView){
//            super(itemView);
//            topData = itemView.findViewById(R.id.top_text);
//            bottomData = itemView.findViewById(R.id.bottom_text);
//        }
//
//        void bind(DataText dataElement){
//            topData.setText(dataElement.mTopText);
//            topData.setTextColor(dataColor);
//            bottomData.setText(dataElement.mBottomText);
//            bottomData.setTextColor(dataColor);
//        }
}

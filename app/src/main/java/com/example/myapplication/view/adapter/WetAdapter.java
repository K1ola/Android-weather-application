//
//package com.example.myapplication.view.adapter;
//
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//
//import androidx.annotation.LayoutRes;
//import androidx.annotation.NonNull;
//import androidx.databinding.DataBindingUtil;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//import com.example.myapplication.BR;
//import com.example.myapplication.databinding.WetItemBinding;
//import com.example.myapplication.model.HolderItem;
//import com.example.myapplication.viewModel.DataViewModel;
//
//import java.util.List;
//
//public class WetAdapter extends RecyclerView.Adapter<WetAdapter.WetHolder> {
//    private DataViewModel dataViewModel;
//    private List<HolderItem> items;
//    private int layoutId;
//
//    public WetAdapter(@LayoutRes int layoutId, DataViewModel data) {
//        this.layoutId = layoutId;
//        this.dataViewModel = data;
//    }
//
//    public void setHolderItems(List<HolderItem> items) {
//        this.items = items;
//    }
//
//    class WetHolder extends RecyclerView.ViewHolder {
//
//        final WetItemBinding binding;
//
//        public WetHolder(WetItemBinding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }
//
//        public void bind(DataViewModel dataViewModel, Integer position) {
//            binding.setViewModel(dataViewModel);
//
//            binding.setVariable(com.example.myapplication.BR.dataViewModel, dataViewModel);
//            binding.setVariable(BR.position, position);
//            binding.executePendingBindings();
//        }
//
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return getLayoutIdForPosition(position);
//    }
//
//    private int getLayoutIdForPosition(int position) {
//        return layoutId;
//    }
//
//    @NonNull
//    @Override
//    public WetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        WetItemBinding binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
//
//        return new WetHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull WetHolder holder, int position) {
//        holder.bind(dataViewModel, position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return items == null ? 0 : items.size();
//    }
//
//}
//

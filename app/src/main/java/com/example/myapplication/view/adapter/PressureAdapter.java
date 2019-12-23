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
//import com.example.myapplication.databinding.PressureItemBinding;
//import com.example.myapplication.model.HolderItem;
//import com.example.myapplication.viewModel.DataViewModel;
//
//import java.util.List;
//
//public class PressureAdapter extends RecyclerView.Adapter<PressureAdapter.PressureHolder> {
//    private DataViewModel dataViewModel;
//    private List<HolderItem> items;
//    private int layoutId;
//
//    public PressureAdapter(@LayoutRes int layoutId, DataViewModel data) {
//        this.layoutId = layoutId;
//        this.dataViewModel = data;
//    }
//
//
//    public void setHolderItems(List<HolderItem> items) {
//        this.items = items;
//    }
//
//    class PressureHolder extends RecyclerView.ViewHolder {
//
//        final PressureItemBinding binding;
//
//        public PressureHolder(PressureItemBinding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }
//
//        public void bind(DataViewModel dataViewModel, Integer position) {
//            binding.setViewModel(dataViewModel);
//
//            binding.setVariable(BR.dataViewModel, dataViewModel);
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
//    public PressureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        PressureItemBinding binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
//
//        return new PressureHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PressureHolder holder, int position) {
//        holder.bind(dataViewModel, position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return items == null ? 0 : items.size();
//    }
//
//}

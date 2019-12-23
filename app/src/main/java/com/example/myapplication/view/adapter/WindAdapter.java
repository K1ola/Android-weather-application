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
//import com.example.myapplication.databinding.WindItemBinding;
//import com.example.myapplication.model.HolderItem;
//import com.example.myapplication.viewModel.DataViewModel;
//
//import java.util.List;
//
//public class WindAdapter extends RecyclerView.Adapter<WindAdapter.WindHolder> {
//    private DataViewModel dataViewModel;
//    private List<HolderItem> items;
//    private int layoutId;
//
//    public WindAdapter(@LayoutRes int layoutId, DataViewModel data) {
//        this.layoutId = layoutId;
//        this.dataViewModel = data;
//    }
//
//    public void setHolderItems(List<HolderItem> items) {
//        this.items = items;
//    }
//
//    class WindHolder extends RecyclerView.ViewHolder {
//
//        final WindItemBinding binding;
//
//        public WindHolder(WindItemBinding binding) {
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
//    public WindHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        WindItemBinding binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
//
//        return new WindHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull WindHolder holder, int position) {
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

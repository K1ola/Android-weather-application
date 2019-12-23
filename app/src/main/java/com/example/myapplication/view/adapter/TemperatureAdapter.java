//
//package com.example.myapplication.view.adapter;
//
//        import android.view.LayoutInflater;
//        import android.view.ViewGroup;
//
//        import androidx.annotation.LayoutRes;
//        import androidx.annotation.NonNull;
//        import androidx.databinding.DataBindingUtil;
//        import androidx.recyclerview.widget.RecyclerView;
//
//
//        import com.example.myapplication.BR;
//        import com.example.myapplication.databinding.TemperatureItemBinding;
//        import com.example.myapplication.model.HolderItem;
//        import com.example.myapplication.viewModel.DataViewModel;
//
//        import java.util.List;
//
//public class TemperatureAdapter extends RecyclerView.Adapter<TemperatureAdapter.TemperatureHolder> {
//    private DataViewModel dataViewModel;
//    private List<HolderItem> items;
//    private int layoutId;
//
//    public TemperatureAdapter(@LayoutRes int layoutId, DataViewModel data) {
//        this.layoutId = layoutId;
//        this.dataViewModel = data;
//    }
//
//
//    public void setHolderItems(List<HolderItem> items) {
//        this.items = items;
//    }
//
//    class TemperatureHolder extends RecyclerView.ViewHolder {
//
//        final TemperatureItemBinding binding;
//
//        public TemperatureHolder(TemperatureItemBinding binding) {
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
//    public TemperatureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        TemperatureItemBinding binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
//
//        return new TemperatureHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TemperatureHolder holder, int position) {
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

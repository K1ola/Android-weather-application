package com.example.myapplication.view.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.SearchFragmentBinding;
import com.example.myapplication.model.Weather;
import com.example.myapplication.view.favorites.FavsFragment;
import com.example.myapplication.viewModel.DataViewModel;

import java.util.List;

public class SearchFragment extends Fragment {
    private DataViewModel viewModel;
    private SearchFragmentBinding searchFragmentBinding;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(getActivity()).get(DataViewModel.class);
        searchFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false);
        final View view = searchFragmentBinding.getRoot();

        ImageButton arrowBack = view.findViewById(R.id.arrow_back);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFavsFragment();
            }
        });

        final RecyclerView recyclerView = view.findViewById(R.id.found_towns);
        final LinearLayoutManager layoutManagerText = new LinearLayoutManager(getContext());
        layoutManagerText.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManagerText);

        EditText editText = view.findViewById(R.id.search_input);

        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!TextUtils.isEmpty(viewModel.searchTown.get())) {
                    List<Weather> w = viewModel.GetFoundTown(s.toString());
                    viewModel.foundTownsAdapter.setData(w, viewModel);
                    recyclerView.setAdapter(viewModel.foundTownsAdapter);
                }
        });

        recyclerView.setAdapter(viewModel.foundTownsAdapter);

        return view;
    }

    private void openFavsFragment(){
        final FavsFragment favsFragment = new FavsFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, favsFragment)
                .commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(DataViewModel.class);
        searchFragmentBinding.setDataViewModel(viewModel);
    }
}

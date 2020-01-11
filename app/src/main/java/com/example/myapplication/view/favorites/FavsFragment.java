package com.example.myapplication.view.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FavoritesFragmentBinding;
import com.example.myapplication.view.main.MainFragment;
import com.example.myapplication.view.search.SearchFragment;
import com.example.myapplication.viewModel.DataViewModel;

public class FavsFragment extends Fragment {
    private DataViewModel viewModel;
    private FavoritesFragmentBinding favoritesFragmentBinding;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(getActivity()).get(DataViewModel.class);
        favoritesFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.favorites_fragment, container, false);
        final View view = favoritesFragmentBinding.getRoot();

        ImageButton arrowBack = view.findViewById(R.id.arrow_back);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainFragment();
            }
        });

        ImageButton searchButton = view.findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearchFragment();
            }
        });

        return view;
    }

    private void openMainFragment(){
        final MainFragment mainFragment = new MainFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, mainFragment)
                .commit();
    }

    private void openSearchFragment(){
        final SearchFragment searchFragment = new SearchFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, searchFragment)
                .commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(DataViewModel.class);
        favoritesFragmentBinding.setDataViewModel(viewModel);
    }
}
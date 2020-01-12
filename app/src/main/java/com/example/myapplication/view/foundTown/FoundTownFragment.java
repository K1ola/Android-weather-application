package com.example.myapplication.view.foundTown;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FoundTownFragmentBinding;
import com.example.myapplication.view.search.SearchFragment;
import com.example.myapplication.viewModel.DataViewModel;

public class FoundTownFragment extends Fragment implements LifecycleOwner {
    private FoundTownFragmentBinding foundTownFragmentBinding;
    private DataViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        foundTownFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.found_town_fragment, container, false);

        View view = foundTownFragmentBinding.getRoot();
        ImageView starIcon = view.findViewById(R.id.favs_star);
        starIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTownToFavs("TODO");
            }
        });

        ImageButton arrowBack = view.findViewById(R.id.arrow_back);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearchFragment();
            }
        });

        viewModel = ViewModelProviders.of(getActivity()).get(DataViewModel.class);
        viewModel.setContext(getActivity());
        foundTownFragmentBinding.setDataViewModel(viewModel);
        if (!viewModel.internet) {
            Toast toast = Toast.makeText(getActivity(),
                    "Нет сети", Toast.LENGTH_LONG);
            toast.show();
        }

        return view;
    }

    private void addTownToFavs(String location) {

    }

    private void openSearchFragment(){
        final SearchFragment searchFragment = new SearchFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, searchFragment)
                .commit();
    }
}

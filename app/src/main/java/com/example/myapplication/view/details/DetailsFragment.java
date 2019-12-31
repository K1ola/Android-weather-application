package com.example.myapplication.view.details;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.databinding.DetailsFragmentBinding;
import com.example.myapplication.view.main.MainFragment;
import com.example.myapplication.viewModel.DataViewModel;

public class DetailsFragment extends Fragment {
    private DetailsFragmentBinding detailsFragmentBinding;
    private DataViewModel viewModel;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        detailsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false);
        final View view = detailsFragmentBinding.getRoot();

        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        viewModel.setContext(getActivity());
        //detailsFragmentBinding.setDataViewModel(viewModel);
//        if (!viewModel.internet) {
//            Toast toast = Toast.makeText(getActivity(),
//                    "Нет сети", Toast.LENGTH_LONG);
//            toast.show();
//        }

        ImageButton arrow_back = view.findViewById(R.id.arrow_back_details);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenMainFragment();
            }
        });

        String dayIndex = "day" + viewModel.currentDay;
        int id = getResources().getIdentifier(dayIndex, "id", getContext().getPackageName());
        TextView day = view.findViewById(id);
        day.setTextColor(Color.RED);

        return view;
    }

    private void OpenMainFragment() {
        MainFragment mainFragment = new MainFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, mainFragment)
                .commit();
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        viewModel.setContext(getActivity());
        detailsFragmentBinding.setDataViewModel(viewModel);
    }

}
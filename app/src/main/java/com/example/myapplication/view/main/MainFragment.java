package com.example.myapplication.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.MainFragmentBinding;
import com.example.myapplication.view.settings.SettingsFragment;
import com.example.myapplication.viewModel.DataViewModel;

public class MainFragment extends Fragment implements LifecycleOwner {
    private MainFragmentBinding mainFragmentBinding;
    private DataViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);

        return mainFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(DataViewModel.class);
        viewModel.setContext(getActivity());
        mainFragmentBinding.setDataViewModel(viewModel);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        ImageView settingsIcon = view.findViewById(R.id.main_setting);
        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingsFragment();
            }
        });

        viewModel = ViewModelProviders.of(getActivity()).get(DataViewModel.class);
        viewModel.setContext(getActivity());
        mainFragmentBinding.setDataViewModel(viewModel);
        if (!viewModel.internet) {
            Toast toast = Toast.makeText(getActivity(),
                    "Нет сети", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void openSettingsFragment() {
        final SettingsFragment settingsFragment = new SettingsFragment();

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, settingsFragment)
                .addToBackStack(null)
                .commit();
    }

}

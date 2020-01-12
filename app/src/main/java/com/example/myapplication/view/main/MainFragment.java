package com.example.myapplication.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.example.myapplication.view.favorites.FavsFragment;
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

        View view = mainFragmentBinding.getRoot();
        ImageView settingsIcon = view.findViewById(R.id.main_setting);
        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingsFragment();
            }
        });

        ImageButton favsButton = view.findViewById(R.id.favs);
        favsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFavsFragment();
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

        LinearLayout mainLinearLayoutCurrent = view.findViewById(R.id.mainLinearLayoutCurrent);
        setEmptyTextIfNull(mainLinearLayoutCurrent);

        LinearLayout mainLinearLayoutCurrentMore = view.findViewById(R.id.mainLinearLayoutCurrentMore);
        setEmptyTextIfNull(mainLinearLayoutCurrentMore);

        LinearLayout mainLinearLayoutHeader = view.findViewById(R.id.mainLinearLayoutHeader);
        setEmptyTextIfNull(mainLinearLayoutHeader);

        return view;
    }

    private void openSettingsFragment() {
        final SettingsFragment settingsFragment = new SettingsFragment();

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, settingsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void openFavsFragment(){
        final FavsFragment favsFragment = new FavsFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, favsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void setEmptyTextIfNull(LinearLayout layout) {
        for( int i = 0; i < layout.getChildCount(); i++ )
            if( layout.getChildAt( i ) instanceof TextView) {
                if ( ((TextView) layout.getChildAt( i )).getText() == null) {
                    ((TextView) layout.getChildAt(i)).setText("");
                }
            }
//        try {
//            if (v instanceof ViewGroup) {
//                ViewGroup vg = (ViewGroup) v;
//                for (int i = 0; i < vg.getChildCount(); i++) {
//                    View child = vg.getChildAt(i);
//                    // recursively call this method
//                    setEmptyTextIfNull(child);
//                }
//            } else if (v instanceof TextView) {
//                if (((TextView) v).getText().toString().isEmpty()) {
//                    ((TextView) v).setText("aaaa");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}

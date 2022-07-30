package com.laioffer.tinnews.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laioffer.tinnews.R;
import com.laioffer.tinnews.repository.NewsRepository;
import com.laioffer.tinnews.repository.NewsViewModelFactory;

// auto create Fragment, no need to new
// Fragment is like View
public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // connect HomeFragment with layout file
        // inflater.inflate -> XML code to java code (XML layout -> java class(FrameLayout = View))
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    // View is from View returned in onCreateView()
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Fragment will implement it's own method
        super.onViewCreated(view, savedInstanceState);

        NewsRepository repository = new NewsRepository();
//        viewModel = new HomeViewModel(repository);
        // use NewsViewModelFactory to create View Model to protect loss data from rotation
        // 本质上是用hash map (ViewModelStore)留住原来的信息
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(HomeViewModel.class);
        viewModel.setCountryInput("us");
        viewModel
                .getTopHeadlines()
                .observe(
                        // in case memory leak, if the fragment end in lifecycle, even it changed later,
                        // will not trigger onChange(see note, another way to write this method)
                        getViewLifecycleOwner(), // fragment provide method. give lifecycle of Fragment to viewModel

                        newsResponse -> {
                            if (newsResponse != null) {
                                Log.d("HomeFragment", newsResponse.toString());
                            }
                        });
    }
}
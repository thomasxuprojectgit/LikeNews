package com.laioffer.tinnews.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.laioffer.tinnews.ui.home.HomeViewModel;
import com.laioffer.tinnews.ui.search.SearchViewModel;

// use one factory to create all view model to protect loss data from different rotation
// fragment is keep changing in ratation, but ViewModel not change
public class NewsViewModelFactory implements ViewModelProvider.Factory {

    private final NewsRepository repository;

    public NewsViewModelFactory(NewsRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(repository); // constructor of initial view model
        } else if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel(repository);
        } else {
            throw new IllegalStateException("Unknown ViewModel");
        }
    }
}



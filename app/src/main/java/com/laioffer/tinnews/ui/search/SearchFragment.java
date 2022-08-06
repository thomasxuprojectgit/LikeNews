package com.laioffer.tinnews.ui.search;

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
import com.laioffer.tinnews.databinding.FragmentSearchBinding;
import com.laioffer.tinnews.repository.NewsRepository;
import com.laioffer.tinnews.repository.NewsViewModelFactory;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;
    private FragmentSearchBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_search, container, false);
        // 使用binding 省略findViewById 的操作
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        SearchNewsAdapter newsAdapter = new SearchNewsAdapter();
        // 用两列展示
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        binding.newsResultsRecyclerView.setLayoutManager(gridLayoutManager);
        binding.newsResultsRecyclerView.setAdapter(newsAdapter);


        // 设置listener 只有点了才会触发
        binding.newsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 每次打字点回车都启动 onQueryTextSubmit
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 现在focus在打字框
                if (!query.isEmpty()) {
                    viewModel.setSearchInput(query);
                }
                // 把上面focus 取消
                binding.newsSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        NewsRepository repository = new NewsRepository();
//        viewModel = new SearchViewModel(repository);
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(SearchViewModel.class);
//        viewModel.setSearchInput("Covid-19");
        viewModel
                .searchNews()
                .observe(
                        getViewLifecycleOwner(),
                        newsResponse -> {
                            if (newsResponse != null) {
                                Log.d("SearchFragment", newsResponse.toString());
                                // 向adaptor填数据
                                newsAdapter.setArticles(newsResponse.articles);
                            }
                        });

        newsAdapter.setItemCallback(article -> {
            SearchFragmentDirections.ActionNavigationSearchToNavigationDetails direction = SearchFragmentDirections.actionNavigationSearchToNavigationDetails(article);
            NavHostFragment.findNavController(SearchFragment.this).navigate(direction);
        });


    }

}
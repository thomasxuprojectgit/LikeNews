package com.laioffer.tinnews.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.laioffer.tinnews.model.Article;
import com.laioffer.tinnews.model.NewsResponse;
import com.laioffer.tinnews.repository.NewsRepository;

// ViewModel is from lifecycle dependency
public class HomeViewModel extends ViewModel {

    private final NewsRepository repository;
    private final MutableLiveData<String> countryInput = new MutableLiveData<>();

    public HomeViewModel(NewsRepository newsRepository) {
        this.repository = newsRepository;
    }

    // get input from View
    public void setCountryInput(String country) {
        countryInput.setValue(country);
    }

    public LiveData<NewsResponse> getTopHeadlines() {
        // List of String (countryInput) -> List of LiveData (newsResponse)
        // :: 代表function reference
        // 必须要这个写，不能直接getTopHeadlines(String country), 原因是一旦View的countryInput改变，就会
        // repository::getTopHeadlines就会被call, 这样不用不停创建observe
        return Transformations.switchMap(countryInput, repository::getTopHeadlines);
    }

    // save article
    public void setFavoriteArticleInput(Article article) {
        repository.favoriteArticle(article);
    }
}

package com.laioffer.tinnews.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laioffer.tinnews.databinding.SearchNewsItemBinding;
import com.laioffer.tinnews.model.Article;
import com.laioffer.tinnews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder> {
    interface ItemCallback {
        void onOpenDetails(Article article);
    }

    private ItemCallback itemCallback;

    public void setItemCallback(ItemCallback itemCallback) {
        this.itemCallback = itemCallback;
    }

    // 1. Supporting data:
    // 传进空list of Artical
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> newsList) {
        // 清空原有的
        articles.clear();
        // 加入所有 的article
        articles.addAll(newsList);
        notifyDataSetChanged();
    }

    // 下面override RecyclerView的三个方法
    // viewType 没用到，区分不同view 种类
    // 上下滑动的时候会一直保持恒定的view holder
    @NonNull
    @Override
    public SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item, parent, false);
        return new SearchNewsViewHolder(view);
    }

    // 上下滑动的时候会不停bind, 和sliding window比较像
    @Override
    public void onBindViewHolder(@NonNull SearchNewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.itemTitleTextView.setText(article.title);
        if (article.urlToImage != null) {
            // load ariticle's URL to image, resize and then input into image holder
            Picasso.get().load(article.urlToImage).resize(200, 200).
                into(holder.itemImageView);
            }
        holder.itemView.setOnClickListener(v -> itemCallback.onOpenDetails(article));

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    // 2. SearchNewsViewHolder:
    // ViewHolder 里面主要存View,
    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImageView;
        TextView itemTitleTextView;

        public SearchNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            SearchNewsItemBinding binding = SearchNewsItemBinding.bind(itemView);
            itemImageView = binding.searchItemImage;
            itemTitleTextView = binding.searchItemTitle;
        }
    }


    // 3. Adapter overrides:
    // TODO
}


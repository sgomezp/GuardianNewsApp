package com.example.android.guardiannewsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sgomezp on 23/06/2017.
 */

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    // Tag for debug messages
    public static final String LOG_TAG = NewsAdapter.class.getSimpleName();

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        public TextView viewHolderHeadline;
        public TextView viewHolderSection;
        public TextView viewHolderDate;
        public TextView viewHolderTime;
        public TextView viewHolderUrl;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview

        public NewsViewHolder(View itemView) {

            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            viewHolderHeadline = (TextView) itemView.findViewById(R.id.news_title);
            viewHolderSection = (TextView) itemView.findViewById(R.id.news_section);
            viewHolderDate = (TextView) itemView.findViewById(R.id.news_date);
            viewHolderTime = (TextView) itemView.findViewById(R.id.news_time);
            viewHolderUrl = (TextView) itemView.findViewById(R.id.news_url);

        }
    }

    // Store a member variable for the contacts
    private ArrayList<News> newsItems;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public NewsAdapter(Context context, ArrayList<News> newsItemsInfo) {
        newsItems = newsItemsInfo;
        mContext = context;
    }

    // Easy access to the context object in the RecyclerView
    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View newsItem =inflater.inflate(R.layout.news_item, parent, false);

        // Return a new holder instance
        NewsViewHolder viewHolder = new NewsViewHolder(newsItem);
        return viewHolder;

    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(NewsAdapter.NewsViewHolder viewHolder, int position) {

        // Get the data model based on position
        News news = newsItems.get(position);

        // Set item views based on your views and data model
        TextView headlineTextView = viewHolder.viewHolderHeadline;
        headlineTextView.setText(news.getHeadline());

        TextView sectionTextView = viewHolder.viewHolderSection;
        sectionTextView.setText(news.getSection());

        TextView dateTextView = viewHolder.viewHolderDate;
        dateTextView.setText(news.getDate());

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {

        Log.i(LOG_TAG, "NewsItem Size is: " + newsItems.size());

        return newsItems.size();
    }

    public void addAll(ArrayList<News> data){
        newsItems.clear();
        newsItems.addAll(data);
        notifyDataSetChanged();
    }

}





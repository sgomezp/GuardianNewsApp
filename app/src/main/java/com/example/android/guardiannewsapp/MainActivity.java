package com.example.android.guardiannewsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    //LOG_TAG for debugging purposes
    public static final String LOG_TAG = MainActivity.class.getName();

    // State of the Boolean var
    static final String STATE_BOOLEAN = "valueOfIfTheFirstTimeOpen";

    /**
     * Constant value for the book loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;

    // URL for The Guardian API data
    private static final String BASE_NEWS_REQUEST_URL =
            "https://content.guardianapis.com/search?q=brexit&from-date=2017-01-01&api-key=test";

    // Max Result for  the query
    private static final String MAX_RESULTS_QUERY = "&maxResults=10";

    //TextView that is displayed when the list
    private TextView mEmptyStateTextView;

    //Adapter for the list of news
    private NewsAdapter mAdapter;

    // Search String variable
    private String userQuery = "";

    // Final URL
    private String finalQueryUrl = "";

    //Boolean var to check df the first time the app is running
    private Boolean isTheFirstTimeOpen = true;

   ArrayList<News> newsItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lookup the RecyclerView in activity layout
        RecyclerView rvNews = (RecyclerView) findViewById(R.id.cardList) ;

        // Create adapter passing in the sample user data
        NewsAdapter adapter = new NewsAdapter(this,newsItems);

        // Attach the adapter to the recyclerview to populate items
        rvNews.setAdapter(adapter);

        // Set layout manager to position the items
        rvNews.setLayoutManager(new LinearLayoutManager(this));
        // That's all!!


        // Empty state
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        if (isTheFirstTimeOpen) {

            mEmptyStateTextView.setText(R.string.before_search);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            Log.i(LOG_TAG, "isTheFirstTimeOpen entro en el if: " + isTheFirstTimeOpen);

        } else {

            android.app.LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, MainActivity.this);
        }

        // The Query to look for
        finalQueryUrl = BASE_NEWS_REQUEST_URL + MAX_RESULTS_QUERY;

        // If there is a network connection, fetch data
        if (isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            Log.i(LOG_TAG, "Estoy conectada!!");

            //Hide the initial empty screen message so the loading indicator will be
            // more visible
            mEmptyStateTextView.setVisibility(View.GONE);

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).

            loaderManager.restartLoader(NEWS_LOADER_ID, null, MainActivity.this);

        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // clear the list. This way the message will be display it if the
            // list has some results for a previous search. Required for reviewer
            //mAdapter.clear();
            newsItems.clear();

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }


    }

    // Check the network connectivity
    public boolean isConnected() {

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(STATE_BOOLEAN, isTheFirstTimeOpen);
    }


    @Override
    public android.content.Loader<List<News>> onCreateLoader(int id, Bundle args) {

        Log.i(LOG_TAG, "Acabo de entrar en onCreateLoader");

        // Create a new loader for the given URL
        // Show loading indicator because the data hasn't been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.VISIBLE);

        // Lookup the RecyclerView in activity layout
        RecyclerView rvNews = (RecyclerView) findViewById(R.id.cardList) ;

        // Create adapter passing in the sample user data
        NewsAdapter adapter = new NewsAdapter(this,newsItems);

        // Attach the adapter to the recyclerview to populate items
        rvNews.setAdapter(adapter);

        // Set layout manager to position the items
        rvNews.setLayoutManager(new LinearLayoutManager(this));
        return new NewsLoader(this, finalQueryUrl);

    }


    @Override
    public void onLoadFinished(android.content.Loader<List<News>> loader, List<News> news) {
        //  give the focus to the main_layout
        findViewById(R.id.main_layout).requestFocus();

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Lookup the RecyclerView in activity layout
        RecyclerView rvNews = (RecyclerView) findViewById(R.id.cardList) ;

        // Create adapter passing in the sample user data
        NewsAdapter adapter = new NewsAdapter(this,newsItems);

        // Attach the adapter to the recyclerview to populate items
        rvNews.setAdapter(adapter);

        // Set layout manager to position the items
        rvNews.setLayoutManager(new LinearLayoutManager(this));


        // Clear the adapter of previous book data
        //adapter.clear();
        newsItems.clear();

        // If there is a valid list of {@link Books}, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
           // mAdapter.addAll(news);
            adapter.addAll(newsItems);
        } else {

            // Set empty state text to display "No books found."
            mEmptyStateTextView.setText(R.string.no_news);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onLoaderReset(android.content.Loader<List<News>> loader) {

        // Loader reset, so we can clear out our existing data.
       //mAdapter.clear();
        newsItems.clear();

    }


}

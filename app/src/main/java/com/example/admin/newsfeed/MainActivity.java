package com.example.admin.newsfeed;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.example.admin.newsfeed.Adapter.FeedAdapter;
import com.example.admin.newsfeed.Connection.HTTPDataHandler;
import com.example.admin.newsfeed.Model.RSSObject;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private RecyclerView mRecyclerView;
    private RSSObject mRSSObject;

    private SwipeRefreshLayout swipeRefreshLayout;

    private final String RSS_LINK = "http://feeds.feedburner.com/digit/latest-from-digit";
    private final String RSS_TO_JSON = " https://api.rss2json.com/v1/api.json?rss_url=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);

        mToolBar = findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolBar.setTitle("NEWS");
            //setActionBar(mToolBar);
            setSupportActionBar(mToolBar);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        loadData();
    }

    private void loadData() {

        AsyncTask<String, String, String> loadDataAsynch = new AsyncTask<String, String, String>() {

            ProgressDialog mProgressDialog = new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {
      //          mProgressDialog.setMessage("Please Wait...");
        //        mProgressDialog.show();
                swipeRefreshLayout.setRefreshing(true);
            }

            @Override
            protected String doInBackground(String... strings) {

                String result;
                HTTPDataHandler httpDataHandler = new HTTPDataHandler();
                result = httpDataHandler.getHTTPData(strings[0]);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
               // mProgressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                mRSSObject = new Gson().fromJson(s, RSSObject.class);
                Log.d("PostExecute","Object Set" + mRSSObject.getStatus() + mRSSObject.toString());
                FeedAdapter feedAdapter = new FeedAdapter(mRSSObject, getBaseContext());

                mRecyclerView.setAdapter(feedAdapter);
                feedAdapter.notifyDataSetChanged();
            }
        };

        StringBuilder stringBuilder = new StringBuilder(RSS_TO_JSON);
        stringBuilder.append(RSS_LINK);
        Log.d("PostExecute",stringBuilder.toString());
        loadDataAsynch.execute(stringBuilder.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh_bt);
            loadData();
        return true;
    }
}

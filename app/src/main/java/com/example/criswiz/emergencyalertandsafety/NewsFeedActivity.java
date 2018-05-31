package com.example.criswiz.emergencyalertandsafety;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.criswiz.emergencyalertandsafety.Adapter.ListSourceAdapter;
import com.example.criswiz.emergencyalertandsafety.Common.Common;
import com.example.criswiz.emergencyalertandsafety.Interface.NewsService;
import com.example.criswiz.emergencyalertandsafety.Model.WebSite;
import com.google.gson.Gson;

import java.util.Objects;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFeedActivity extends AppCompatActivity {

    RecyclerView listWebsite;
    RecyclerView.LayoutManager layoutManager;
    NewsService jService;
    ListSourceAdapter adapter;
    AlertDialog dialog;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        //init cache
        Paper.init(this);

        //Init Service
        jService = Common.getNewsService();

        //Init View
        swipeRefreshLayout = findViewById(R.id.swipeReferesh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebsiteSource(true);
            }
        });

        listWebsite = findViewById(R.id.list_source);
        listWebsite.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listWebsite.setLayoutManager(layoutManager);

        dialog = new SpotsDialog(this);

        loadWebsiteSource(false);
    }

    private void loadWebsiteSource(boolean isRefreshed) {
        if (!isRefreshed){
            String cache = Paper.book().read("cache");
            if (cache != null && !cache.isEmpty() && !cache.equals("null")){
                WebSite webSite = new Gson().fromJson(cache, WebSite.class);//Convert cache from json to object
                adapter = new ListSourceAdapter(getBaseContext(), webSite);
                adapter.notifyDataSetChanged();
                listWebsite.setAdapter(adapter);
            }else {
                dialog.show();
                //Fetch new data
                jService.getSources().enqueue(new Callback<WebSite>() {
                    @Override
                    public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                        adapter = new ListSourceAdapter(getBaseContext(), response.body());
                        adapter.notifyDataSetChanged();
                        listWebsite.setAdapter(adapter);

                        //save to cache
                        Paper.book().write("cache", new Gson().toJson(response.body()));

                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<WebSite> call, Throwable t) {
                        Toast.makeText(NewsFeedActivity.this, "Failed to fetch new data 1", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }else {
            swipeRefreshLayout.setRefreshing(true);
            //Fetch new data
            jService.getSources().enqueue(new Callback<WebSite>() {
                @Override
                public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                    adapter = new ListSourceAdapter(getBaseContext(), response.body());
                    adapter.notifyDataSetChanged();
                    listWebsite.setAdapter(adapter);

                    //save to cache
                    Paper.book().write("cache", new Gson().toJson(response.body()));

                    //Dismiss refresh
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<WebSite> call, Throwable t) {
                    Toast.makeText(NewsFeedActivity.this, "Failed to fetch new data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}


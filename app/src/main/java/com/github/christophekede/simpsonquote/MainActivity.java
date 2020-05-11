package com.github.christophekede.simpsonquote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.github.christophekede.simpsonquote.adapter.ListAdapter;
import com.github.christophekede.simpsonquote.api.quote.QuoteCallback;
import com.github.christophekede.simpsonquote.api.quote.QuoteInterface;
import com.github.christophekede.simpsonquote.core.toast.Toaster;
import com.github.christophekede.simpsonquote.server.ApiCaller;
import com.github.christophekede.simpsonquote.server.ApiCallerResponse;
import com.github.christophekede.simpsonquote.server.QuoteResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//dev
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String BASE_URL = "https://thesimpsonsquoteapi.glitch.me/";
    private ApiCaller caller = new ApiCaller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        caller = new ApiCaller();

        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }

        mAdapter = new ListAdapter(input);
        recyclerView.setAdapter(mAdapter);
        initialQuotes();



    }

    private void initialQuotes(){
        caller.getRandomQuote(new QuoteCallback() {
            @Override
            public void onSuccess(ApiCallerResponse resp) {
                if(resp.getStatus() == "ok"){
                    Toaster.showToast(getApplicationContext(), "Yesysy");
                }
            }

            @Override
            public void onError(@NonNull Throwable throwable) {

            }
        });

    }

}

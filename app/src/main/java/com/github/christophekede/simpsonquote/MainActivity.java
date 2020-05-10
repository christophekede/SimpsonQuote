package com.github.christophekede.simpsonquote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.github.christophekede.simpsonquote.adapter.ListAdapter;
import com.github.christophekede.simpsonquote.api.quote.QuoteInterface;
import com.github.christophekede.simpsonquote.core.toast.Toaster;
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
    private Toaster toaster = new Toaster();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        makeApiCall();



    }

    private void makeApiCall(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        QuoteInterface quoteInterface = retrofit.create(QuoteInterface.class);

        Call<List<QuoteResponse>> call = quoteInterface.loadQuotes(1);

        call.enqueue(new Callback<List<QuoteResponse>>() {
            @Override
            public void onResponse(Call<List<QuoteResponse>> call, Response<List<QuoteResponse>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    List<QuoteResponse> test = response.body();
                    List<String> input = new ArrayList<>();

                    for(int i = 0; i< test.size(); i++){
                        input.add(test.get(i).getQuote());
                    }
                    Toast.makeText(getApplicationContext(),"Api sucess", Toast.LENGTH_SHORT).show();


                    mAdapter = new ListAdapter(input);
                    recyclerView.setAdapter(mAdapter);

                }else{
                    Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<List<QuoteResponse>> call, Throwable t) {
                toaster.showToast(getApplicationContext(), "Error");

            }
        });
    }

}

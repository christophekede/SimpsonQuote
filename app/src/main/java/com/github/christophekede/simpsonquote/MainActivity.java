package com.github.christophekede.simpsonquote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = getSharedPreferences("simpsonquote", Context.MODE_PRIVATE);

        caller = new ApiCaller();

        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        List<QuoteResponse> quoteList = getDataFromCache();
        if(quoteList != null){
            showList(quoteList);
        }else{
            initialQuotes();
        }







    }

    private List<QuoteResponse> getDataFromCache() {
        String jsonQuote= sharedPref.getString("jsonQuote", null );

        if(jsonQuote == null) return  null;
        Type listType = new TypeToken<List<QuoteResponse>>(){}.getType();
        return caller.getGson().fromJson(jsonQuote, listType);
    }

    private void initialQuotes(){
        caller.get10Quotes(new QuoteCallback() {
            @Override
            public void onSuccess(ApiCallerResponse resp) {
                if(resp.getStatus() == "ok"){
                    Toaster.showToast(getApplicationContext(), "Yesysy");
                    showList(resp.getQuotes());
                    saveQuotes(resp.getQuotes());

                }
            }

            @Override
            public void onError(@NonNull Throwable throwable) {

            }
        });

    }

    private void saveQuotes(List<QuoteResponse> quotes) {
        String jsonString = caller.getGson().toJson(quotes);
        sharedPref.edit()
                .putString("jsonQuote", jsonString )
                .apply();

        Toaster.showToast(getApplicationContext(), "List Saved");
    }

    private void showList(List<QuoteResponse> listQuote){
        mAdapter = new ListAdapter(listQuote);
        recyclerView.setAdapter(mAdapter);
    }

}

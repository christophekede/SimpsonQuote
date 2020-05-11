package com.github.christophekede.simpsonquote.server;

import android.widget.Toast;

import androidx.annotation.Nullable;

import com.github.christophekede.simpsonquote.adapter.ListAdapter;
import com.github.christophekede.simpsonquote.api.quote.QuoteCallback;
import com.github.christophekede.simpsonquote.api.quote.QuoteInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiCaller {
    private static final String BASE_URL = "https://thesimpsonsquoteapi.glitch.me/";
    private static ApiCallerResponse res;


    private Gson gson;
    private Retrofit retrofit;
    private QuoteInterface quoteInterface;

    public ApiCaller(){
        res = new ApiCallerResponse("ko", null);;
         gson = new GsonBuilder()
                .setLenient()
                .create();

         retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

         quoteInterface = retrofit.create(QuoteInterface.class);


    }


    public ApiCallerResponse getRandomQuote( @Nullable final QuoteCallback callback){
        Call<List<QuoteResponse>> call = quoteInterface.loadRandomQuote();

        call.enqueue(new Callback<List<QuoteResponse>>() {
            @Override
            public void onResponse(Call<List<QuoteResponse>> call, Response<List<QuoteResponse>> response) {
                if(response.isSuccessful() && response.body() !=null){
                    List<QuoteResponse> quote = response.body();
                    callback.onSuccess(new ApiCallerResponse("ok", quote));


                }else{
                    res = new ApiCallerResponse("ko", null);

                }

            }

            @Override
            public void onFailure(Call<List<QuoteResponse>> call, Throwable t) {
                callback.onError(t);

                res = new ApiCallerResponse("ko", null);

            }
        });
        return res;
        
    }

    public void post(){ }

}

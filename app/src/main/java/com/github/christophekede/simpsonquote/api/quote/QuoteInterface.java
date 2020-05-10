package com.github.christophekede.simpsonquote.api.quote;

import com.github.christophekede.simpsonquote.server.QuoteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuoteInterface {

    @GET("/quotes")
    Call<List<QuoteResponse>> loadQuotes(@Query("count") int numberOfQuotes);
}

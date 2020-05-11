package com.github.christophekede.simpsonquote.server;

import java.util.List;

public class ApiCallerResponse {


    private String status;
    private List<QuoteResponse> quotes;

    public  ApiCallerResponse(String status, List<QuoteResponse> quotes){
        this.status = status;
        this.quotes = quotes;
    }

    public String getStatus() {
        return status;
    }

    public List<QuoteResponse> getQuotes() {
        return quotes;
    }
}

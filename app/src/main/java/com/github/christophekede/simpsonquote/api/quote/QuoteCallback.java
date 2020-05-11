package com.github.christophekede.simpsonquote.api.quote;

import androidx.annotation.NonNull;

import com.github.christophekede.simpsonquote.server.ApiCallerResponse;

public interface QuoteCallback {
    void onSuccess(ApiCallerResponse resp);

    void onError(@NonNull Throwable throwable);
}

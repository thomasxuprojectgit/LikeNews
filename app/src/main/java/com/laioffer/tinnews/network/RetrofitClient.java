package com.laioffer.tinnews.network;

// client like chrome, convert responses from backend to java obj and UI
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String API_KEY = "add your API key";
    private static final String BASE_URL = "https://newsapi.org/v2/";


    public static Retrofit newInstance() {
        // Retrofit use OkHttpClient to get network three handshake network connection
        // .addInterceptor(new HeaderInterceptor()) 打断working flow, 中间加步骤
        // OkHttp is used for most android for http request, it is retrofit's dependency
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .build();
        // build URL for API request
        // .addConverterFactory(GsonConverterFactory.create()) the json converter use gson, it is actual json worker
        // .client(okHttpClient) is the actual http worker
        // Retrofit is like agent
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    // 1: url -> http -> http
    // 2: url - > url + header -> http -> json
    // here is 2, add header to http request before sending
    private static class HeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request request = original
                    .newBuilder()
                    .header("X-Api-Key", API_KEY)
                    .build();
            return chain.proceed(request);
        }
    }
}


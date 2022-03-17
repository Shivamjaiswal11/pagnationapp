package com.example.pagenationapp.Network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public static final String API_BASE_URL = "https://api.unsplash.com";
    public static final String API_URL = "VWtpDilrxjxrqrjOMYlc0kdvjGVFT2T3GmzGn91t3KI";

    private static OkHttpClient httpClient;

    static {
        httpClient = new OkHttpClient();
    }

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}

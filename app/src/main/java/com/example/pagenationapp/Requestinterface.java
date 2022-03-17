package com.example.pagenationapp;

import static com.example.pagenationapp.Network.ServiceGenerator.API_URL;

import com.example.pagenationapp.Model1.ImageModel;
import com.example.pagenationapp.Model1.SerachModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Requestinterface {

    @Headers("Authorization: Client-ID "+API_URL)
    @GET("photos")
    Call<List<ImageModel>> getimage(
            @Query("page") int page,
            @Query("pre_page") int perpage

    );
    @Headers("Authorization: Client-ID "+API_URL)
    @GET("Search/photos")
    Call<SerachModel> searchimage(
            @Query("query") String query


    );
}

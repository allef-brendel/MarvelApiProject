package com.e.marvelapiproject.retrofit;

import com.e.marvelapiproject.pojo.QuadrinhosResposta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface APIInterface {

    @GET("comics")
    Call<QuadrinhosResposta> getQuadrinhos(@Query("apikey") String apikey,
                                           @Query("ts") String ts,
                                           @Query("hash") String hash);

    @GET("comics/{characterId}")
    Call<List<QuadrinhosResposta>> getQuadrinho(@Path ("characterId") String characterId,
                                                @Query("apikey") String apikey,
                                                @Query("ts") String ts,
                                                @Query("hash") String hash);

}


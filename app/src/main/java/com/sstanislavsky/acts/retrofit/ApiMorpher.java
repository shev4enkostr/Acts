package com.sstanislavsky.acts.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by stanislav on 2/8/18.
 */

public interface ApiMorpher {
    @GET("/{language}/declension")
    Call<MorpherResponse> getData(
            @Path("language") String language,
            @Query("s") String declensionString,
            @Query("flags") String flags,
            @Query("format") String format);
}

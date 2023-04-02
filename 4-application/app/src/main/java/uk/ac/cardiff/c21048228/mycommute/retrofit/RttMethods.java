package uk.ac.cardiff.c21048228.mycommute.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RttMethods {

    @GET("search/{from}/to/{to}")
    Call<SearchModel> getAllData(@Path("from") String from, @Path("to") String to);
}

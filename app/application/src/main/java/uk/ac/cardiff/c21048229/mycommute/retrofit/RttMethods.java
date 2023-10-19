package uk.ac.cardiff.c21048229.mycommute.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface RttMethods {

    @GET("search/{from}/to/{to}")
    Call<SearchModel> getAllData(@Path("from") String from, @Path("to") String to, @Header("Authorization") String auth);

    @GET("search/{from}/to/{to}/{year}/{month}/{day}/{time}")
    Call<SearchModel> getAllDataWithTime(@Path("from") String from, @Path("to") String to, @Path("year") String year, @Path("month") String month, @Path("day") String day, @Path("time") String time, @Header("Authorization") String auth);
}

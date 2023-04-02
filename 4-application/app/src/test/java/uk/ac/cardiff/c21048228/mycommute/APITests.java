package uk.ac.cardiff.c21048228.mycommute;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import retrofit2.Call;
import uk.ac.cardiff.c21048228.mycommute.retrofit.RttMethods;
import uk.ac.cardiff.c21048228.mycommute.retrofit.RttRetroFit;
import uk.ac.cardiff.c21048228.mycommute.retrofit.SearchModel;

public class APITests {
    @Test
    public void testAPI() {

        RttMethods rttMethods = RttRetroFit.getRetrofitInstance().create(RttMethods.class);
        Call<SearchModel> call = rttMethods.getAllData("CYS", "CDF");
        call.enqueue(new retrofit2.Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, retrofit2.Response<SearchModel> response) {
                System.out.println("Success " + response.code());
                System.out.println(response.body().getServices().get(0).getDestination().get(0).getPublicTime());
                assertEquals(200, response.code());
                assertEquals("CATHAYS", response.body().getLocation().getTiploc());
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
                System.out.println("Failure");
            }
        });

    }
}

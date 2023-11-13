package es.upm.flights.models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AirportsAPI {

    @GET("suggest")
    Call<ApiResponse> getAirports(
            @Query("q") String query,
            @Query("api_key") String apiKey,
            @Query("lang") String lang
    );

}

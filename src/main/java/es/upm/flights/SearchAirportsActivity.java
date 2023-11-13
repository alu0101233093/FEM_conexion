package es.upm.flights;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import es.upm.flights.models.Airport;
import es.upm.flights.models.AirportsAPI;
import es.upm.flights.models.ApiResponse;
import es.upm.flights.views.AirportAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchAirportsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Airport> airports = new ArrayList<Airport>();
    final String API_KEY = "4a709096-ed12-4f3d-bd43-ade3ea9c9628";
    final String LANG = "es";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_airports);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final AirportAdapter recyclerAdapter = new AirportAdapter(airports);
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void getAirports(View v){
        EditText campoBusqueda = findViewById(R.id.airport_edit_text);
        String busqueda = campoBusqueda.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://airlabs.co/api/v9/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AirportsAPI airportsAPI = retrofit.create(AirportsAPI.class);
        Call<ApiResponse> call = airportsAPI.getAirports(busqueda, API_KEY, LANG);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e("API", "Código: " + response.code());
                    return;
                }
                ApiResponse responseObject = response.body();
                airports = responseObject.getResponse().getAirports();
                airports = airports.stream()
                        .filter(airport -> airport.getIataCode() != null)
                        .collect(Collectors.toList());
                final AirportAdapter recyclerAdapter = new AirportAdapter(airports);
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API", "Mensaje: " + t.getMessage());
            }
        });
    }

    public void backButton(View v){
        finish();
    }

}


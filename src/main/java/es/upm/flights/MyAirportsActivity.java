package es.upm.flights;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.upm.flights.models.Airport;
import es.upm.flights.views.MyAirportAdapter;

public class MyAirportsActivity extends AppCompatActivity {

    private String TAG = "MyAirports";
    private DatabaseReference mDB;
    private FirebaseAuth mAuth;
    private String uid;
    private ArrayList<Airport> airports;
    private RecyclerView recycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_airports);
        mDB = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();

        recycler = findViewById(R.id.my_airports_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        getAirports();
    }

    public void getAirports(){
        mDB.child("users").child(uid).child("airports").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(getBaseContext(), "Datos encontrados.", Toast.LENGTH_SHORT).show();
                    Log.println(Log.INFO,TAG,"Datos encontrados.");
                    airports = new ArrayList<>();
                    for (DataSnapshot aeropuertoSnapshot : dataSnapshot.getChildren()) {
                        airports.add(aeropuertoSnapshot.getValue(Airport.class));
                    }

                    // Crear y configurar el adaptador después de obtener los datos
                    MyAirportAdapter adapter = new MyAirportAdapter(airports);
                    recycler.setAdapter(adapter);
                } else {
                    Toast.makeText(getBaseContext(), "Datos no encontrados.", Toast.LENGTH_SHORT).show();
                    Log.println(Log.INFO,TAG,"Datos no encontrados.");
                    // El usuario no tiene aeropuertos en su lista
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar errores si es necesario
            }
        });
    }

    public void backButton(View v){
        finish();
    }
}

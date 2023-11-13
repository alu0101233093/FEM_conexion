package es.upm.flights.views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import es.upm.flights.R;
import es.upm.flights.models.Airport;

public class AirportAdapter extends RecyclerView.Adapter<AirportAdapter.ViewHolder> {

    private List<Airport> airports;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String userID;
    private Context context;
    public AirportAdapter(List<Airport> airports) {
        this.airports = airports;
    }

    @NonNull
    @Override
    public AirportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid(); //userID = "1234";
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.airport_item,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AirportAdapter.ViewHolder holder, int position) {
        holder.asignarDatos(airports.get(position));
    }

    @Override
    public int getItemCount() {
        return airports.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView iata;
        private TextView name;
        private ImageButton btn;
        private int position;
        private Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iata = itemView.findViewById(R.id.AirportItemIATA);
            this.name = itemView.findViewById(R.id.AirportItemName);
            this.btn = itemView.findViewById(R.id.saveBtn);
            this.context = itemView.getContext();
            this.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.println(Log.INFO,"ViewHolder","Aeropuerto: " + name.getText());
                    comprobarUsuarioDB();
                    comprobarAeropuertoDB(position);
                }
            });
        }

        public void asignarDatos(Airport airport) {
            this.iata.setText(airport.getIataCode());
            this.name.setText(airport.getName());
        }
    }

    public void comprobarUsuarioDB(){
        mDatabase.child("users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // El usuario está registrado, puedes continuar con la siguiente verificación o la inserción del aeropuerto
                } else {
                    // El usuario no está registrado, realiza las acciones necesarias
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores si es necesario
            }
        });
    }

    public void comprobarAeropuertoDB(int position){
        String iata = airports.get(position).getIataCode();
        mDatabase.child("users").child(userID).child("aeropuertos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.hasChild(iata)) {
                    Toast.makeText(context, "Aeropuerto " + iata + " ya está guardado",Toast.LENGTH_SHORT).show();
                } else {
                    guardarAeropuertoDB(position);
                    Toast.makeText(context, "Aeropuerto " + iata + " guardado",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores si es necesario
            }
        });
    }

    public void guardarAeropuertoDB(int position){
        String iata = airports.get(position).getIataCode();
        mDatabase.child("users").child(userID).child("airports").child(iata).setValue(airports.get(position))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Éxito al agregar el aeropuerto
                    } else {
                        // Fallo al agregar el aeropuerto, task.getException() contiene detalles sobre el error
                    }
                });
    }
}


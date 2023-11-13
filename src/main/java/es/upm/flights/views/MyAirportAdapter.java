package es.upm.flights.views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import es.upm.flights.R;
import es.upm.flights.models.Airport;

public class MyAirportAdapter extends RecyclerView.Adapter<MyAirportAdapter.ViewHolderMyAirport>{

    ArrayList<Airport> airports;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String userID;
    private Context context;
    public MyAirportAdapter(ArrayList<Airport> airports) {
        this.airports = airports;
    }

    @NonNull
    @Override
    public MyAirportAdapter.ViewHolderMyAirport onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid(); //userID = "1234";
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_airports_item,null,false);
        return new ViewHolderMyAirport(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAirportAdapter.ViewHolderMyAirport holder, int position) {
        holder.asignarDatos(airports.get(position), position);
    }

    @Override
    public int getItemCount() {
        return airports.size();
    }

    public class ViewHolderMyAirport extends RecyclerView.ViewHolder {

        TextView info;
        String iata;
        int position;
        ImageButton deleteButton;

        public ViewHolderMyAirport(@NonNull View itemView) {
            super(itemView);
            this.info = itemView.findViewById(R.id.MyAirportItem);
            this.deleteButton = itemView.findViewById(R.id.deleteBtn);
            this.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.println(Log.INFO,"ViewHolder","Aeropuerto: " + iata);
                    eliminarAeropuerto(iata, position);
                }
            });
        }

        public void asignarDatos(Airport airport, int position) {
            this.info.setText(airport.toString());
            this.iata = airport.getIataCode();
            this.position = position;
        }
    }

    public void eliminarAeropuerto(String iata, int position){
        DatabaseReference referenciaElemento = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("airports").child(iata);

        referenciaElemento.removeValue()
                .addOnSuccessListener(aVoid -> {
                    // Éxito al eliminar el elemento
                    Log.d("Firebase", "Elemento eliminado correctamente");
                    Toast.makeText(context, "Aeropuerto " + iata + " eliminado",Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Fallo al eliminar el elemento, e contiene detalles sobre el error
                    Log.e("Firebase", "Error al eliminar el elemento", e);
                });
        airports.remove(position);
    }
}

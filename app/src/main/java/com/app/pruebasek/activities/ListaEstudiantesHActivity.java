package com.app.pruebasek.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.app.pruebasek.R;
import com.app.pruebasek.adapters.ListaEstudiantesHAdapter;
import com.app.pruebasek.adapters.ListaEstudiantesMAdapter;
import com.app.pruebasek.includes.MyToolbart;
import com.app.pruebasek.modelos.Estudiante;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ListaEstudiantesHActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ListaEstudiantesHAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_estudiantes_h);
        MyToolbart.show(this,"Ver horarios",true);
        mRecyclerView = findViewById(R.id.recyclerViewListaEstudiantes3);
        //layout manager ojo
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //boton
    }
    @Override
    protected void onStart() {
        super.onStart();

        //OBTENER LOS HISTORY BOOKING DONDE EL ID DEL CLIENTE SEA IGUAL AL IDE DE LA SESION INICIADA
        Query query = FirebaseDatabase.getInstance().getReference()
                .child("Estudiantes");
        FirebaseRecyclerOptions<Estudiante> options = new FirebaseRecyclerOptions.Builder<Estudiante>()
                .setQuery(query,Estudiante.class)
                .build();

        mAdapter = new ListaEstudiantesHAdapter(options, ListaEstudiantesHActivity.this);

        //usar el recycler
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening(); //DEJA DE OIR LOS CAMBIOS
    }
}
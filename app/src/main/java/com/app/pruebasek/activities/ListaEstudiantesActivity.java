package com.app.pruebasek.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.pruebasek.R;
import com.app.pruebasek.adapters.ListaEstudiantesAdapter;
import com.app.pruebasek.includes.MyToolbart;
import com.app.pruebasek.modelos.Estudiante;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ListaEstudiantesActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FloatingActionButton mButtonNuevoEstudiante;
    private ListaEstudiantesAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_estudiantes);
        MyToolbart.show(this,"Lista de estudiantes",true);
        mRecyclerView = findViewById(R.id.recyclerViewListaEstudiantes);
        //layout manager ojo
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //boton
        mButtonNuevoEstudiante = findViewById(R.id.floatingActionButtonNuevoEstudiante);
        //acciones
        mButtonNuevoEstudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                nuevoEstudiante();
            }
        });
    }

    private void nuevoEstudiante()
    {
        Intent intent = new Intent(ListaEstudiantesActivity.this, RegistroEstudianteActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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

        mAdapter = new ListaEstudiantesAdapter(options, ListaEstudiantesActivity.this);

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
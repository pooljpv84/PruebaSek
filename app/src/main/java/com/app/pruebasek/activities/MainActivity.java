package com.app.pruebasek.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.pruebasek.R;

public class MainActivity extends AppCompatActivity {
    CardView cardEstudiantes;
    CardView cardMaterias;
    CardView cardHorarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //instancias
        cardEstudiantes = findViewById(R.id.cardEstudiantes);
        cardMaterias = findViewById(R.id.cardMaterias);
        cardHorarios= findViewById(R.id.cardHorario);
        //acciones
        cardEstudiantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirEstudiantes();
            }
        });
        cardMaterias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirEstudiantesM();
            }
        });
        cardHorarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirEstudiantesH();
            }
        });


    }

    private void abrirEstudiantes()
    {
        Intent intent = new Intent(MainActivity.this, ListaEstudiantesActivity.class);
        startActivity(intent);
    }
    private void abrirEstudiantesM()
    {
        Intent intent = new Intent(MainActivity.this, ListaEstudiantesMActivity.class);
        startActivity(intent);
    }
    private void abrirEstudiantesH()
    {
        Intent intent = new Intent(MainActivity.this, ListaEstudiantesHActivity.class);
        startActivity(intent);
    }


}
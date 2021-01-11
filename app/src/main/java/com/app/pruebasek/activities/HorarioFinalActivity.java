package com.app.pruebasek.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.app.pruebasek.R;

public class HorarioFinalActivity extends AppCompatActivity {
    String mExtraid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario_final);
        mExtraid = getIntent().getStringExtra("idUserSeleccionado");
        Log.i("sms","sms"+mExtraid);
    }
}
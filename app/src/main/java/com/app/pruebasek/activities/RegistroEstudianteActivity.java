package com.app.pruebasek.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.pruebasek.R;
import com.app.pruebasek.includes.MyToolbart;
import com.app.pruebasek.modelos.Estudiante;
import com.app.pruebasek.providers.EstudianteProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmax.dialog.SpotsDialog;

public class RegistroEstudianteActivity extends AppCompatActivity {
    TextInputEditText mTextInputNombresEst;
    TextInputEditText mTextInputApellidosEst;
    TextInputEditText mTextInputTelefonoEst;
    TextInputEditText mTextInputCedulaEst;
    Button mbtnRegisterEst;
    EstudianteProvider mEstudianteProvider;
    DatabaseReference mDatabase;
    AlertDialog mDialog2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_estudiante);
        MyToolbart.show(this,"Nuevo Estudiante",true);

        mTextInputNombresEst = findViewById(R.id.TextInputNombresEst);
        mTextInputApellidosEst= findViewById(R.id.TextInputApellidosEst);
        mTextInputTelefonoEst= findViewById(R.id.TextInputTelefonoEst);
        mTextInputCedulaEst= findViewById(R.id.TextInputCedulaEst);
        mbtnRegisterEst = findViewById(R.id.btnRegisterEst);
        //FIREBASE
        mEstudianteProvider = new EstudianteProvider();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //FIN FIREBASE
        mbtnRegisterEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickEstudiante();
            }
        });

    }

    private void clickEstudiante()
    {
        final String cedula= mTextInputCedulaEst.getText().toString();
        final String nombres = mTextInputNombresEst.getText().toString();
        final String apellidos = mTextInputApellidosEst.getText().toString();
        final String telefono = mTextInputTelefonoEst.getText().toString();

        if (!nombres.isEmpty() && !apellidos.isEmpty() && !String.valueOf(telefono).isEmpty()
                && !cedula.isEmpty()  /*&& validarCedula(cedula)*/)
        {
            if (telefono.length()==10)
            {

                String telefono2 = String.valueOf(telefono);
                String cedula2 = String.valueOf(cedula);
                Estudiante estudiante = new Estudiante(cedula2,nombres,apellidos,telefono2);
                Log.i("sms","exito");
                registrarEstudiante(estudiante);


            }
            else
            {
                Toast.makeText(this, "El telefono debe tener al menos 10 caracteres", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void registrarEstudiante(Estudiante estudiante)
    {
        mEstudianteProvider.create(estudiante).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {

                    Intent intent = new Intent(RegistroEstudianteActivity.this, ListaEstudiantesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Log.i("sms"," id est: "+estudiante.getIdEstudiante());
                    Toast.makeText(RegistroEstudianteActivity.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Log.i("sms","error al registrar estudiante");
                }
            }
        });
    }
}
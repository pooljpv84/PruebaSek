package com.app.pruebasek.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.app.pruebasek.R;
import com.app.pruebasek.includes.MyToolbart;
import com.app.pruebasek.modelos.Materia;
import com.app.pruebasek.providers.EstudianteProvider;
import com.app.pruebasek.providers.MateriasProvider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.satsuware.usefulviews.LabelledSpinner;

import java.util.ArrayList;
import java.util.List;

public class RegistrarMatriculaActivity extends AppCompatActivity {
    String mExtracedula;
    TextInputEditText mTextInputNombresEst;
    TextInputEditText mTextInputApellidosEst;
    TextInputEditText mTextInputTelefonoEst;
    TextInputEditText mTextInputCedulaEst;
    EstudianteProvider mEstudianteProvider;
    MateriasProvider mMateriasProvider;
    String midMateria;
    ArrayList<String>listaMaterias;
    LabelledSpinner mspinnerMateria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_matricula);
        mExtracedula = getIntent().getStringExtra("idUserSeleccionado");
        MyToolbart.show(this,"Matricular estudiante",true);
        //INSTANCIAS
        mTextInputNombresEst = findViewById(R.id.TextInputNombresEst);
        mTextInputApellidosEst= findViewById(R.id.TextInputApellidosEst);
        mTextInputTelefonoEst= findViewById(R.id.TextInputTelefonoEst);
        mTextInputCedulaEst= findViewById(R.id.TextInputCedulaEst);
        mspinnerMateria = findViewById(R.id.spinnerMateria);
        mTextInputNombresEst.setFocusable(false);
        mTextInputApellidosEst.setFocusable(false);
        mTextInputTelefonoEst.setFocusable(false);
        mTextInputCedulaEst.setFocusable(false);
        //firebase
        mEstudianteProvider = new EstudianteProvider();
        mMateriasProvider = new MateriasProvider();
        consultar(mExtracedula);
        loadMaterias();
        Log.i("sms","sms: "+mExtracedula);
    }

    private void loadMaterias()
    {

        final List<Materia> materiaList = new ArrayList<Materia>();
        mMateriasProvider.getMaterias("Materias")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot ds: dataSnapshot.getChildren())
                    {
                        midMateria = ds.getKey();
                        String nombre = ds.child("nombreMateria").getValue().toString();

                        Log.i("sms",""+nombre);
                        Log.i("sms",""+midMateria);
                        //Especies es = new Especies();
                        //es.setIdEspecie(id);
                        //es.setNombreEspecie(nombre);
                        materiaList.add(new Materia(midMateria,nombre));


                    }
                    opcionesMaterias(materiaList);
                } }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void opcionesMaterias(final List<Materia> materiasList){
        listaMaterias = new ArrayList<String>();
        listaMaterias.add("Seleccione");
        for (int i=0; i<materiasList.size();i++){
            listaMaterias.add(String.valueOf(materiasList.get(i).getNombreMateria()));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RegistrarMatriculaActivity.this, android.R.layout.simple_spinner_dropdown_item,listaMaterias);
        mspinnerMateria.setCustomAdapter(arrayAdapter);
        mspinnerMateria.setOnItemChosenListener(new LabelledSpinner.OnItemChosenListener() {
            @Override
            public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
                if (position == 0)
                {
                    mspinnerMateria.setColor(R.color.colorPlomo);
                //    loadRazas(especiesList,position);
                }
                else
                {
                  //  mspinnerEspecie.setColor(R.color.colorAccent);

                }
                /*for (int i=0;i<especiesList.size();i++)
                {
                    if ((position) == Integer.parseInt(especiesList.get(i).getIdEspecie())){
                        Log.i("sms aa "," "+especiesList.get(i).getIdEspecie());
                        loadRazas(especiesList,position);
                    }
                }*/
                //Snackbar.make(labelledSpinner, "Clicked " + adapterView.getItemAtPosition(position), Snackbar.LENGTH_LONG).show();
            }
            @Override
            public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

            }
        });
    }

    private void consultar(String cedula)
    {
        mEstudianteProvider.getEstudiante(cedula).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String nombre = dataSnapshot.child("nombresEstudiante").getValue().toString();
                    String apellido = dataSnapshot.child("apellidosEstudiante").getValue().toString();
                    String telefono = dataSnapshot.child("telefonoEstudiante").getValue().toString();
                    mTextInputNombresEst.setText(nombre);
                    mTextInputApellidosEst.setText(apellido);
                    mTextInputTelefonoEst.setText(telefono);
                    mTextInputCedulaEst.setText(mExtracedula);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
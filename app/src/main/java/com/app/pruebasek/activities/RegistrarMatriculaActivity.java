package com.app.pruebasek.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.app.pruebasek.R;
import com.app.pruebasek.includes.MyToolbart;
import com.app.pruebasek.modelos.Horario;
import com.app.pruebasek.modelos.Materia;
import com.app.pruebasek.modelos.Matricula;
import com.app.pruebasek.providers.EstudianteProvider;
import com.app.pruebasek.providers.HorariosProvider;
import com.app.pruebasek.providers.MateriasProvider;
import com.app.pruebasek.providers.MatriculaProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
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
    HorariosProvider mHorariosProvider;
    MatriculaProvider mMatriculaProvider;
    String midMateria;
    ArrayList<String>listaMaterias, listaHorarios, listaidHorarios; ;
    LabelledSpinner mspinnerMateria, mspinnerHorario;
    String idFinalHorario;
    Button mbtnMatricularEst;
    long idMatricula=0;
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
        mspinnerHorario = findViewById(R.id.spinnerHorario);
        mbtnMatricularEst = findViewById(R.id.btnMatricularEst);
        mTextInputNombresEst.setFocusable(false);
        mTextInputApellidosEst.setFocusable(false);
        mTextInputTelefonoEst.setFocusable(false);
        mTextInputCedulaEst.setFocusable(false);
        //firebase
        mEstudianteProvider = new EstudianteProvider();
        mMateriasProvider = new MateriasProvider();
        mHorariosProvider = new HorariosProvider();
        mMatriculaProvider = new MatriculaProvider();
        consultar(mExtracedula);
        loadMaterias();
        Log.i("sms","sms: "+mExtracedula);
        mbtnMatricularEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
            {
            clickMatricular();    
            }
        });

    }

    private void clickMatricular()
    {
    final String idEstudiante = mTextInputCedulaEst.getText().toString();
        final String nombresEstudiante = mTextInputNombresEst.getText().toString();
        final String apellidosEstudiante= mTextInputApellidosEst.getText().toString();
        final String telefonoEstudiante= mTextInputTelefonoEst.getText().toString();
        final int posicionmateria = mspinnerMateria.getSpinner().getSelectedItemPosition();
        final int posicionhorario = mspinnerHorario.getSpinner().getSelectedItemPosition();
        if (!idEstudiante.isEmpty() && !nombresEstudiante.isEmpty() && !apellidosEstudiante.isEmpty()
                && !telefonoEstudiante.isEmpty()
                && posicionmateria != 0 && posicionhorario!= 0 )
        {
            Matricula matricula = new Matricula(String.valueOf(incrementoId()+1),idFinalHorario,String.valueOf(posicionmateria),mExtracedula);
            registrarMatricula(matricula);
        }
        else{
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    private void registrarMatricula(final Matricula matricula)
    {
        mMatriculaProvider.create(matricula).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {

                    Intent intent = new Intent(RegistrarMatriculaActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Log.i("sms"," id matricula: "+matricula.getIdMatricula());
                    Toast.makeText(RegistrarMatriculaActivity.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Log.i("sms","error al registrar matricula");
                }
            }
        });
    }

    private long incrementoId()
    {
        mMatriculaProvider.getMatriculadata().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    idMatricula = dataSnapshot.getChildrenCount();
                    Log.i("sms contador ",""+idMatricula);
                }
                else
                {
                    idMatricula = 0;
                    Log.i("sms contador else ",""+idMatricula);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return idMatricula;
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
                    loadHorarios(materiasList,position);
                }
                else
                {
                    mspinnerMateria.setColor(R.color.colorAccent);

                }
                for (int i=0;i<materiasList.size();i++)
                {
                    if ((position) == Integer.parseInt(materiasList.get(i).getIdMateria())){
                        Log.i("sms aa "," "+materiasList.get(i).getIdMateria());
                        loadHorarios(materiasList,position);
                    }
                }
                //Snackbar.make(labelledSpinner, "Clicked " + adapterView.getItemAtPosition(position), Snackbar.LENGTH_LONG).show();
            }
            @Override
            public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView)
            {

            }
        });
    }
    private void loadHorarios(final List<Materia> materiasList, final int position)
    {

        final List<Horario> horariosList = new ArrayList<Horario>();

        mHorariosProvider.getHorarios("Horarios")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot ds: dataSnapshot.getChildren())
                    {

                        String idHorario = ds.getKey();
                        String dia = ds.child("dia").getValue().toString();
                        String hora = ds.child("hora").getValue().toString();
                        String idMateria = ds.child("idMateria").getValue().toString();

                        horariosList.add(new Horario(idHorario,dia,hora,idMateria));

                    }
                    opcionesHorarios(horariosList, materiasList,position);
                }else
                {
                    Log.i("sms lista","error combo horarios");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void opcionesHorarios(List<Horario> horariosList, List<Materia> materiasList, int position){
        listaHorarios = new ArrayList<String>();
        listaidHorarios = new ArrayList<String>();
        listaHorarios.add("Seleccione");
        listaidHorarios.add("Seleccione");

        //istaRazas.clear();
        for (int x=0;x <horariosList.size();x++){
            for (int y=0;y<materiasList.size();y++){

                if (horariosList.get(x).getIdMateria().equals(materiasList.get(y).getIdMateria())
                        && String.valueOf(position).equals(horariosList.get(x).getIdMateria())) {

                    Log.i("sms equals "," raja: "+horariosList.get(x).getIdHorario());
                    listaHorarios.add(String.valueOf(horariosList.get(x).getIdHorario())+". "+String.valueOf(horariosList.get(x).getDia())+" - "+String.valueOf(horariosList.get(x).getHora()));
                    listaidHorarios.add(String.valueOf(horariosList.get(x).getIdHorario()));
                }else{
                    Log.i("sms equals "," borrado "+x);
                    //listaRazas.clear();
                }
                if (position == 0)
                {
                    listaHorarios.clear();
                    listaidHorarios.clear();
                    listaHorarios.add("Seleccione");
                    listaidHorarios.add("Seleccione");

                }

            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RegistrarMatriculaActivity.this, android.R.layout.simple_spinner_dropdown_item,listaHorarios);
        mspinnerHorario.setCustomAdapter(arrayAdapter);

        mspinnerHorario.setOnItemChosenListener(new LabelledSpinner.OnItemChosenListener() {
            @Override
            public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
                if (position == 0)
                {
                    mspinnerHorario.setColor(R.color.colorPlomo);
                }
                else
                {
                    mspinnerHorario.setColor(R.color.colorAccent);
                    idFinalHorario = listaidHorarios.get(position).toString();
                    //String[] parts = idFinalRaza.split(" - ");
                    //part1 = parts[0];
                }


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
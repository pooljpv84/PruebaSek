package com.app.pruebasek.providers;



import com.app.pruebasek.modelos.Matricula;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MatriculaProvider
{
    DatabaseReference mDatabase;

    public MatriculaProvider()
    {
        //instancia de mdatabase
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Matricula");
        //hago referencia a los nodos de la bdd de firebase
    }
    public DatabaseReference getMatriculadata(){
        return mDatabase;
    }

    public Task<Void> create(Matricula matricula)
    {
        //filtro el objeto por clave y valor para ingresar datos en el Firebase
        Map<String, Object> map = new HashMap<>();
        map.put("idMatricula", matricula.getIdMatricula());
        map.put("idHorario", matricula.getIdHorario());
        map.put("idMateria", matricula.getIdMateria());
        map.put("idEstudiante", matricula.getIdEstudiante());

        return mDatabase.child(matricula.getIdMatricula()).setValue(map);
    }

    public DatabaseReference getMatricula(String idMatricula){
        return mDatabase.child(idMatricula);
    }

    //para la imagen
    public Task<Void> update(Matricula matricula)
    {
        //filtro el objeto por clave y valor para ingresar datos en el Firebase
        Map<String, Object> map = new HashMap<>();
        map.put("idMatricula", matricula.getIdMatricula());
        map.put("idHorario", matricula.getIdHorario());
        map.put("idMateria", matricula.getIdMateria());
        map.put("idEstudiante", matricula.getIdEstudiante());
        return mDatabase.child(matricula.getIdMatricula()).updateChildren(map);
    }

    public Task<Void> delete(String id) {
        return mDatabase.child(id).removeValue();
    }

}
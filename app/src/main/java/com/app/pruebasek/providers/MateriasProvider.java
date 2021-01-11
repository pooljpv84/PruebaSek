package com.app.pruebasek.providers;

import com.app.pruebasek.modelos.Materia;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MateriasProvider
{
    DatabaseReference mDatabase;

    public MateriasProvider()
    {
        //instancia de mdatabase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //hago referencia a los nodos de la bdd de firebase
    }

    public DatabaseReference getMaterias(String materia){
        return mDatabase.child(materia);
    }

    public Task<Void> create(Materia materia)
    {
        //filtro el objeto por clave y valor para ingresar datos en el Firebase
        Map<String, Object> map = new HashMap<>();
        map.put("idMateria", materia.getIdMateria());
        map.put("nombreMateria", materia.getNombreMateria());
        return mDatabase.child("Materias").child(materia.getIdMateria()).setValue(map);
    }
    public Task<Void> update(Materia materia)
    {
        //filtro el objeto por clave y valor para ingresar datos en el Firebase
        Map<String, Object> map = new HashMap<>();
        map.put("nombreMateria", materia.getNombreMateria());
        return mDatabase.child("Materias").child(materia.getIdMateria()).updateChildren(map);
    }

}

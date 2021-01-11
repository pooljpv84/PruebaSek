package com.app.pruebasek.providers;



import com.app.pruebasek.modelos.Estudiante;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class EstudianteProvider {
    DatabaseReference mDatabase;

    public EstudianteProvider() {
        //instancia de mdatabase
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Estudiantes");
        //hago referencia a los nodos de la bdd de firebase
    }

    public Task<Void> create(Estudiante estudiante) {
        return mDatabase.child(estudiante.getIdEstudiante()).setValue(estudiante);
    }
    public DatabaseReference getEstudiante(String idEstudiante)
    {
        return mDatabase.child(idEstudiante);
    }
    //para la imagen
    public Task<Void> update(Estudiante estudiante)
    {
        //filtro el objeto por clave y valor para ingresar datos en el Firebase
        Map<String, Object> map = new HashMap<>();
        map.put("nombresEstudiante", estudiante.getNombresEstudiante());
        map.put("apellidosEstudiante", estudiante.getApellidosEstudiante());
        map.put("telefonoEstudiante", estudiante.getTelefonoEstudiante());

        return mDatabase.child(String.valueOf(estudiante.getIdEstudiante())).updateChildren(map);
    }

    public Task<Void> delete(String id) {
        return mDatabase.child(id).removeValue();
    }


}
package com.app.pruebasek.providers;


import com.app.pruebasek.modelos.Horario;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class HorariosProvider
{
    private DatabaseReference mDatabase;

    public HorariosProvider()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public DatabaseReference getHorarios(String nodohorario){
        return mDatabase.child(nodohorario);
    }

    public Task<Void> create(Horario horario)
    {
        return mDatabase.child(horario.getIdHorario()).setValue(horario);
        //Razas insertara lo del constructor
    }

    //actualizar razas
    public Task<Void> updateHorario(String idHorario, String dia,String hora, String idMateria) {

        Map<String, Object> map = new HashMap<>();
        map.put("dia", dia);
        map.put("hora", hora);
        map.put("idMateria", idMateria);
        return mDatabase.child(idHorario).updateChildren(map);
    }

    //ver si ya se creo la informacion para no sobreescribirla
    public DatabaseReference getHorario2(String idHorario)
    {
        return mDatabase.child(idHorario);
    }






}

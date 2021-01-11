package com.app.pruebasek.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.pruebasek.R;
import com.app.pruebasek.activities.HorarioFinalActivity;
import com.app.pruebasek.activities.RegistrarMatriculaActivity;
import com.app.pruebasek.modelos.Estudiante;
import com.app.pruebasek.modelos.Matricula;
import com.app.pruebasek.providers.EstudianteProvider;
import com.app.pruebasek.providers.HorariosProvider;
import com.app.pruebasek.providers.MateriasProvider;
import com.app.pruebasek.providers.MatriculaProvider;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ListaEstudiantesHAdapter extends FirebaseRecyclerAdapter<Matricula, ListaEstudiantesHAdapter.ViewHolder>
{

    private MatriculaProvider mMatriculaProvider;
    private EstudianteProvider mEstudianteProvider;
    private MateriasProvider mMateriasProvider;
    private HorariosProvider mHorariosProvider;
    private Context mContext;
    public ListaEstudiantesHAdapter(@NonNull FirebaseRecyclerOptions<Matricula> options, Context context) {
        super(options);
        mMatriculaProvider = new MatriculaProvider();
        mEstudianteProvider = new EstudianteProvider();
        mMateriasProvider = new MateriasProvider();
        mHorariosProvider = new HorariosProvider();
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ListaEstudiantesHAdapter.ViewHolder holder, int position, @NonNull Matricula matricula)
    {
        //id del la lista de estudiantes
        final String id = getRef(position).getKey();

        //acede a los campos
        holder.textViewName.setText(matricula.getIdEstudiante());


        mMatriculaProvider.getMatricula(matricula.getIdMatricula().toString())

                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){
                            String idEstudiante = dataSnapshot.child("idEstudiante").getValue().toString();
                            String idMatricula = dataSnapshot.child("idMatricula").getValue().toString();
                            String idMateria = dataSnapshot.child("idMateria").getValue().toString();
                            String idHorario = dataSnapshot.child("idHorario").getValue().toString();
                            sacarNombre(idEstudiante,holder);
                            sacarMateria(idMateria,holder);
                            sacarHorario(idHorario,holder);
                            Log.i("sms: ","id materia: "+idMateria);
                            Log.i("sms: ","id horario: "+idHorario);

               }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        //al dar click en el elemento del cardview
        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, HorarioFinalActivity.class);
                intent.putExtra("idUserSeleccionado",id);
                mContext.startActivity(intent);
            }
        });

    }

    private void sacarNombre(String idEstudiante, @NonNull final ListaEstudiantesHAdapter.ViewHolder holder)
    {

    mEstudianteProvider.getEstudiante(idEstudiante).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            if (dataSnapshot.exists())
            {
                String nombre = dataSnapshot.child("nombresEstudiante").getValue().toString();
                String apellidos = dataSnapshot.child("apellidosEstudiante").getValue().toString();
                holder.textViewName.setText(nombre+" "+apellidos);

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

    }
    private void sacarMateria(String idMateria, @NonNull final ListaEstudiantesHAdapter.ViewHolder holder)
    {
        mMateriasProvider.getNodoMaterias(idMateria).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists())
                        {
                            String nombreMateria = dataSnapshot.child("nombreMateria").getValue().toString();

                            holder.textviewmateria.setText(nombreMateria);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void sacarHorario(String idHorario, @NonNull final ListaEstudiantesHAdapter.ViewHolder holder)
    {
        mHorariosProvider.getHorario2(idHorario).

                addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists())
                        {
                            String dia = dataSnapshot.child("dia").getValue().toString();
                            String hora = dataSnapshot.child("hora").getValue().toString();

                            holder.textViewDia.setText(dia+" - "+hora);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    @NonNull
    @Override
    public ListaEstudiantesHAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lista_matriculas,parent,false);
        return new ListaEstudiantesHAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private View mview;
        private TextView textViewName, textviewmateria, textViewDia;



        public ViewHolder(View view)
        {
            super(view);
            mview = view;
            textViewName = view.findViewById(R.id.textViewName);
            textviewmateria = view.findViewById(R.id.textViewMateria);
            textViewDia = view.findViewById(R.id.textViewDia);


        }
    }
}


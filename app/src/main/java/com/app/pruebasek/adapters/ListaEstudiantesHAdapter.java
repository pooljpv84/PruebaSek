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
import com.app.pruebasek.providers.MatriculaProvider;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ListaEstudiantesHAdapter extends FirebaseRecyclerAdapter<Matricula, ListaEstudiantesHAdapter.ViewHolder>
{

    private MatriculaProvider mMatriculaProvider;
    private Context mContext;
    public ListaEstudiantesHAdapter(@NonNull FirebaseRecyclerOptions<Matricula> options, Context context) {
        super(options);
        mMatriculaProvider = new MatriculaProvider();
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
                        Log.i("sms","saa");
                        if (dataSnapshot.exists()){
                            String idEstudiante = dataSnapshot.child("idEstudiante").getValue().toString();

                            String idMatricula = dataSnapshot.child("idMatricula").getValue().toString();


                            holder.textViewName.setText(idEstudiante);
                            holder.textviewcedula.setText(idMatricula);

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

    @NonNull
    @Override
    public ListaEstudiantesHAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lista_estudiantes,parent,false);
        return new ListaEstudiantesHAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private View mview;
        private TextView textViewName, textviewcedula;



        public ViewHolder(View view)
        {
            super(view);
            mview = view;
            textViewName = view.findViewById(R.id.textViewName);
            textviewcedula = view.findViewById(R.id.textViewCedula);


        }
    }
}

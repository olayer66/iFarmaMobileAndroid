package fdi.ucm.adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fdi.ucm.ifarmamobile.R;
import fdi.ucm.model.Mensaje;

import static fdi.ucm.ifarmamobile.R.id.cv;

/**
 * Created by joset on 16/05/2017.
 */

public class MensajeAdapter  extends RecyclerView.Adapter<MensajeAdapter.PersonViewHolder>{

    List<Mensaje> mensajes;

    public MensajeAdapter(List<Mensaje> mensajes){
        this.mensajes = mensajes;

    }
    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView asunto;
        TextView remitente;
        TextView fecha;
        ImageView leido;
        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            asunto = (TextView)itemView.findViewById(R.id.asunto);
            remitente=(TextView) itemView.findViewById(R.id.remitente);
            fecha=(TextView) itemView.findViewById(R.id.cardCorreoFecha);
            leido=(ImageView) itemView.findViewById(R.id.correoNuevo);
        }
    }
    @Override
    public int getItemCount() {
        return mensajes.size();
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_correo, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }
    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.asunto.setText(mensajes.get(i).getAsunto());
        String remitente=mensajes.get(i).getRemitente().getNombre()+" "+mensajes.get(i).getRemitente().getApellidos();
        personViewHolder.remitente.setText(remitente);
        personViewHolder.fecha.setText(mensajes.get(i).getFecha());
        personViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if(!mensajes.get(i).getleido())
        {
            personViewHolder.leido.setVisibility(View.VISIBLE);
        }
    }
}

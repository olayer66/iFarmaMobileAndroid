package fdi.ucm.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fdi.ucm.ifarmamobile.R;
import fdi.ucm.model.Mensaje;

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

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            asunto = (TextView)itemView.findViewById(R.id.asunto);
        }
    }
    @Override
    public int getItemCount() {
        return mensajes.size();
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_correo_medico, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }
    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.asunto.setText(mensajes.get(i).getAsunto());
        personViewHolder.remitente.setText(mensajes.get(i).getRemitente());
    }
}

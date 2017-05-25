package fdi.ucm.adapters;

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



public class MensajeAdapter  extends RecyclerView.Adapter<MensajeAdapter.PersonViewHolder>{

    private List<Mensaje> mensajes;
    private OnCorreoSelected mListener;

    public MensajeAdapter(List<Mensaje> mensajes){
        this.mensajes = mensajes;

    }
    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView asunto;
        TextView remitente;
        TextView fecha;
        ImageView leido;
        TextView id;
        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvCorreo);
            asunto = (TextView)itemView.findViewById(R.id.cardCorreoAsunto);
            remitente=(TextView) itemView.findViewById(R.id.cardCorreoRemitente);
            fecha=(TextView) itemView.findViewById(R.id.cardCorreoFecha);
            leido=(ImageView) itemView.findViewById(R.id.cardCorreoNuevo);
            id=(TextView) itemView.findViewById(R.id.cardCorreoId);
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
    public void onBindViewHolder(PersonViewHolder personViewHolder,final int i) {
        personViewHolder.asunto.setText(mensajes.get(i).getAsunto());
        String remitente=mensajes.get(i).getRemitente().getNombre()+" "+mensajes.get(i).getRemitente().getApellidos();
        personViewHolder.remitente.setText(remitente);
        personViewHolder.fecha.setText(mensajes.get(i).getFecha());
        personViewHolder.id.setText(String.valueOf(i));
        personViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener=(OnCorreoSelected)v.getContext();
                String remitente=mensajes.get(i).getRemitente().getNombre()+" "+mensajes.get(i).getRemitente().getApellidos();
                mListener.OnCorreoSelected(mensajes.get(i).getAsunto(),
                                           remitente,
                                           mensajes.get(i).getFecha(),
                                           mensajes.get(i).getMensaje());
            }
        });
        if(!mensajes.get(i).getleido())
        {
            personViewHolder.leido.setVisibility(View.VISIBLE);
        }
    }
    public interface OnCorreoSelected {
        void OnCorreoSelected(String asunto, String remitente, String fecha, String mensaje);
    }
}

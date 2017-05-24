package fdi.ucm.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fdi.ucm.ifarmamobile.R;
import fdi.ucm.model.Tratamiento;

/**
 * Created by joset on 22/05/2017.
 */

public class TratamientoMedicoAdapter extends RecyclerView.Adapter<TratamientoMedicoAdapter.PersonViewHolder>{

    private ArrayList<Tratamiento> tratamientos;

    public TratamientoMedicoAdapter(ArrayList<Tratamiento> tratamientos){
        this.tratamientos = tratamientos;

    }
    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView medicamento;
        TextView tomas;
        TextView fechaFin;
        TextView id;
        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvTratamientoMedico);
            medicamento = (TextView)itemView.findViewById(R.id.cardTratamientoMedicoMedicamento);
            fechaFin=(TextView) itemView.findViewById(R.id.cardTratamientoMedicoFechaFin);
            tomas=(TextView) itemView.findViewById(R.id.cardTratamientoMedicoTomas);
            id=(TextView) itemView.findViewById(R.id.cardTratamientoMedicoId);
        }
    }
    @Override
    public int getItemCount() {
        return tratamientos.size();
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_paciente, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }
    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.medicamento.setText(tratamientos.get(i).getMedicamento().getNombre());
        String tomas=tratamientos.get(i).getNumDosisDia()+" cada " +tratamientos.get(i).getPerioicidad()+" horas";
        personViewHolder.tomas.setText(tomas);
        personViewHolder.fechaFin.setText(tratamientos.get(i).getFechaFinTratamiento().toString());
        personViewHolder.id.setText(String.valueOf(i));
        personViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

package fdi.ucm.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fdi.ucm.model.Tratamiento;

/**
 * Created by joset on 22/05/2017.
 */

public class TratamientoMedicoAdapter extends RecyclerView.Adapter<TratamientoMedicoAdapter.PersonViewHolder>{

    private List<Tratamiento> tratamientos;

    public TratamientoMedicoAdapter(List<Tratamiento> tratamientos){
        this.tratamientos = tratamientos;

    }
    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView medicamento;
        TextView tratamiento;
        TextView fechaFin;
        TextView id;
        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvPaciente);
            paciente = (TextView)itemView.findViewById(R.id.cardPacienteNombre);
            telefono=(TextView) itemView.findViewById(R.id.cardPacienteTelefono);
            tratamiento=(TextView) itemView.findViewById(R.id.);
            id=(TextView) itemView.findViewById(R.id.cardPacienteId);
        }
    }
    @Override
    public int getItemCount() {
        return pacientes.size();
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_paciente, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }
    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        String paciente=pacientes.get(i).getNombre()+" "+pacientes.get(i).getApellidos();
        personViewHolder.paciente.setText(paciente);
        personViewHolder.telefono.setText(pacientes.get(i).getTelefono());
        personViewHolder.id.setText(String.valueOf(pacientes.get(i).getIdUsuario()));
        personViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

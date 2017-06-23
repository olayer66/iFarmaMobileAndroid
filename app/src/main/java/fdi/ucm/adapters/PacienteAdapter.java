package fdi.ucm.adapters;
import android.content.Context;
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
import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Paciente;
import fdi.ucm.model.Tratamiento;
import fdi.ucm.model.Usuario;


public class PacienteAdapter  extends RecyclerView.Adapter<PacienteAdapter.PersonViewHolder>{

    private List<Paciente> pacientes;
    private ArrayList<Medicamento> medicamentos;
    private OnPacienteSelected mListener;
    public PacienteAdapter(List<Paciente> pacientes,ArrayList<Medicamento> medicamentos){
        this.pacientes = pacientes;
        this.medicamentos= medicamentos;
    }
    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView paciente;
        TextView telefono;
        ImageView tratamiento;
        TextView id;
        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvPaciente);
            paciente = (TextView)itemView.findViewById(R.id.cardPacienteNombre);
            telefono=(TextView) itemView.findViewById(R.id.cardPacienteTelefono);
            tratamiento=(ImageView) itemView.findViewById(R.id.cardPacienteTratamiento);
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
    public void onBindViewHolder(PersonViewHolder personViewHolder, final int i) {
        String paciente=pacientes.get(i).getNombre()+" "+pacientes.get(i).getApellidos();
        personViewHolder.paciente.setText(paciente);
        personViewHolder.telefono.setText(pacientes.get(i).getTelefono());
        personViewHolder.id.setText(String.valueOf(pacientes.get(i).getId()));
        if(pacientes.get(i).getTratamiento().size()==0)
            personViewHolder.tratamiento.setVisibility(View.INVISIBLE);
        else {

        }

        personViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener=(OnPacienteSelected) v.getContext();
                String paciente=pacientes.get(i).getNombre()+" "+pacientes.get(i).getApellidos();
                mListener.OnPacienteSelected(pacientes.get(i),medicamentos);
            }
        });
    }
    public interface OnPacienteSelected {
        void OnPacienteSelected(Paciente paciente,ArrayList<Medicamento> medicamentos);
    }

}
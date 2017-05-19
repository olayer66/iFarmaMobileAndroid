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
import fdi.ucm.model.Usuario;


public class PacienteAdapter  extends RecyclerView.Adapter<PacienteAdapter.PersonViewHolder>{

    private List<Usuario> pacientes;

    public PacienteAdapter(List<Usuario> pacientes){
        this.pacientes = pacientes;

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
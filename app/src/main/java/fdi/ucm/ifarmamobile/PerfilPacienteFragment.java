package fdi.ucm.ifarmamobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Paciente;


public class PerfilPacienteFragment extends Fragment {

    private static final String ARG_PACIENTE = "paciente";
    private static final String ARG_MEDICAMENTOS = "medicamentos";

    private Paciente pac;
    private ArrayList<Medicamento> medi;

    public static PerfilPacienteFragment newInstance(Paciente paciente, ArrayList<Medicamento> medicamentos) {
        PerfilPacienteFragment fragment = new PerfilPacienteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PACIENTE, paciente);
        args.putParcelableArrayList(ARG_MEDICAMENTOS, medicamentos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pac = getArguments().getParcelable(ARG_PACIENTE);
            medi = getArguments().getParcelable(ARG_MEDICAMENTOS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_perfil_paciente, container, false);


        final TextView nombre = (TextView) view.findViewById(R.id.detallePacienteNombreCompleto);
        final TextView telefono  = (TextView) view.findViewById(R.id.detallePacienteTelefono);
        final TextView email  = (TextView) view.findViewById(R.id.detallePacienteCorreo);
        final TextView direccion  = (TextView) view.findViewById(R.id.detallePacienteDireccion);
        final TextView ciudad  = (TextView) view.findViewById(R.id.detallePacienteCiudad);
        final TextView codpostal  = (TextView) view.findViewById(R.id.detallePacienteCodPostal);
        final TextView provincia  = (TextView) view.findViewById(R.id.detallePacienteProvincia);
        final TextView comunidadAutonoma  = (TextView) view.findViewById(R.id.detallePacienteComAutonoma);
        final TextView medico  = (TextView) view.findViewById(R.id.detallePacienteMedico);
        final Bundle args = getArguments();
        Paciente paciente= args.getParcelable(ARG_PACIENTE);

        nombre.setText(paciente.getNombre()+" "+paciente.getApellidos());
        telefono.setText(paciente.getTelefono());
        email.setText(paciente.getEmail());
        direccion.setText(paciente.getDireccion());
        ciudad.setText(paciente.getCiudad());
        codpostal.setText(paciente.getCodPostal());
        provincia.setText(paciente.getProvincia());
        comunidadAutonoma.setText(paciente.getComAutonoma());
        medico.setText(paciente.getMedico().getNombre() + " " + paciente.getMedico().getApellidos());
        return view;
    }
}

package fdi.ucm.ifarmamobile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import fdi.ucm.model.Paciente;
import fdi.ucm.model.Tratamiento;


/**
 * A simple {@link Fragment} subclass.
 */
public class NuevoTratamientoFragment extends Fragment {

    private static final String TAG = "detallePaciente";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PACIENTE= "paciente";

    // TODO: Rename and change types of parameters
    private Paciente paciente;

    public static NuevoTratamientoFragment newInstance(Paciente paciente) {
        NuevoTratamientoFragment fragment = new NuevoTratamientoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PACIENTE,paciente);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paciente=getArguments().getParcelable(ARG_PACIENTE);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_nuevo_tratamiento, container, false);
        Spinner medicamento=(Spinner) view.findViewById(R.id.nuevoTratamientoMedicamentos);

        Button anadir=(Button) view.findViewById(R.id.nuevoTratamientoAnadir);
        final Bundle args=getArguments();
        anadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tratamiento tratamiento= new Tratamiento(Long.parseLong("0"),args.getParcelable(ARG_PACIENTE),)
            }
        });
        return view;
    }

}

package fdi.ucm.ifarmamobile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Paciente;
import fdi.ucm.model.Tratamiento;

import static fdi.ucm.ifarmamobile.R.string.medicamento;


/**
 * A simple {@link Fragment} subclass.
 */
public class NuevoTratamientoFragment extends Fragment {

    private static final String TAG = "detallePaciente";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PACIENTE= "paciente";
    private static  final String ARG_MEDICAMENTOS="medicamentos";

    // TODO: Rename and change types of parameters
    private Paciente paciente;
    private List<Medicamento> medicamentos;

    public static NuevoTratamientoFragment newInstance(Paciente paciente, ArrayList<Medicamento> medicamentos) {
        NuevoTratamientoFragment fragment = new NuevoTratamientoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PACIENTE,paciente);
        args.putParcelableArrayList(ARG_MEDICAMENTOS,medicamentos);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paciente=getArguments().getParcelable(ARG_PACIENTE);
            medicamentos= getArguments().getParcelableArrayList(ARG_MEDICAMENTOS);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_nuevo_tratamiento, container, false);
        //Spinner
        Spinner medicamento=(Spinner) view.findViewById(R.id.nuevoTratamientoMedicamentos);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_personal, cargarSpinner());
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        medicamento.setAdapter(adapter);

        //EditText
        EditText periodicidad=(EditText) view.findViewById(R.id.nuevoTratamientoPeriodicidad);
        EditText numPastillas= (EditText) view.findViewById(R.id.nuevoTratamientoPastillas);
        EditText fechaFin;

        //Boton enviar
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
    private List<String> cargarSpinner()
    {
        List<String> nombres= new ArrayList<>();
        Iterator<Medicamento> iter= medicamentos.iterator();
        while (iter.hasNext())
        {
            nombres.add(iter.next().getNombre());
        }
        return nombres;
    }
}

package fdi.ucm.ifarmamobile;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import fdi.ucm.adapters.MensajeAdapter;
import fdi.ucm.adapters.TratamientoMedicoAdapter;
import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Paciente;
import fdi.ucm.model.Tratamiento;
import fdi.ucm.model.Usuario;

import static android.R.attr.id;
import static android.R.attr.tabStripEnabled;
import static android.R.id.list;
import static fdi.ucm.ifarmamobile.R.string.correo;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetallePacienteFragment extends Fragment  {
    protected RecyclerView mRecyclerView;
    protected TratamientoMedicoAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    private static final String TAG = "detallePaciente";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PACIENTE= "paciente";
    private static  final String ARG_MEDICAMENTOS="medicamentos";

    // TODO: Rename and change types of parameters
    private Paciente paciente;
    private ArrayList<Medicamento> medicamentos;
    private OnNuevoTratamiento mListener;
    private ArrayList<Tratamiento> tratamientos;

    public static DetallePacienteFragment newInstance(Paciente paciente, ArrayList<Medicamento> medicamentos) {
        DetallePacienteFragment fragment = new DetallePacienteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PACIENTE, paciente);
        args.putParcelableArrayList(ARG_MEDICAMENTOS,medicamentos);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paciente = getArguments().getParcelable(ARG_PACIENTE);
            medicamentos= getArguments().getParcelableArrayList(ARG_MEDICAMENTOS);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_detalle_paciente, container, false);
        //Datos del paciente
        final TextView nombre = (TextView) view.findViewById(R.id.detallePacienteNombre);
        final TextView telefono = (TextView) view.findViewById(R.id.detallePacienteTelefono);
        final TextView email = (TextView) view.findViewById(R.id.detallePacienteCorreo);
        final ImageButton anadir=(ImageButton) view.findViewById(R.id.detallePacienteBotonAñadir);
        //Cargamos la vista
        final Bundle args = getArguments();
        final Paciente paciente= args.getParcelable(ARG_PACIENTE);
        nombre.setText(paciente.getNombre()+" "+paciente.getApellidos());
        telefono.setText(paciente.getTelefono());
        email.setText(paciente.getEmail());
        //cardView del tratamiento
        Activity activity=getActivity();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvTratamientoMedico);
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<Tratamiento> tr= new ArrayList<>(paciente.getTratamiento());
        if(tr==null)
            tr= new ArrayList<>();
        mAdapter = new TratamientoMedicoAdapter(tr);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        anadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener=(OnNuevoTratamiento) v.getContext();
                ArrayList<Medicamento> medicamentos= args.getParcelableArrayList(ARG_MEDICAMENTOS);
                mListener.OnNuevoTratamiento(paciente,medicamentos);
            }
        });
        return view;
    }

    public ArrayList<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(ArrayList<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }

    public TratamientoMedicoAdapter getmAdapter() {
        return mAdapter;
    }

    public interface OnNuevoTratamiento {
        void OnNuevoTratamiento(Paciente paciente, ArrayList<Medicamento> medicamentos);
    }
}

package fdi.ucm.ifarmamobile;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fdi.ucm.Propiedades;
import fdi.ucm.adapters.PacienteAdapter;
import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Medico;
import fdi.ucm.model.Paciente;
import fdi.ucm.model.Tratamiento;
import fdi.ucm.model.Usuario;

public class ListaPacientesFragment extends Fragment {
    private ArrayList<Paciente> pacientes;
    private ArrayList<Medicamento> medicamentos;
    private static final String TAG = "listaPacientes";
    protected RecyclerView mRecyclerView;
    protected PacienteAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    private static final String ARG_MEDICO= "medico";
    private static final String ARG_MEDICAMENTOS= "medicamentos";

    public static ListaPacientesFragment newInstance(Medico medico, ArrayList<Medicamento> medicamentos) {
        ListaPacientesFragment fragment = new ListaPacientesFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MEDICO, medico);
        args.putParcelableArrayList(ARG_MEDICAMENTOS,medicamentos);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cargarDatos();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lista_pacientes, container, false);
        rootView.setTag(TAG);
        Activity activity=getActivity();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvPacientes);
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PacienteAdapter(pacientes,medicamentos);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity=getActivity();
    }
    //Carga con volley los mensajes desde la BBDD
    private void cargarDatos()
    {
        final Bundle args = getArguments();
        Medico medico=args.getParcelable(ARG_MEDICO);
        medicamentos=args.getParcelableArrayList(ARG_MEDICAMENTOS);
        pacientes=medico.getListaPacientes();
    }
}
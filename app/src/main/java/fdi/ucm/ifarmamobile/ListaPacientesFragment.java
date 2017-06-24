package fdi.ucm.ifarmamobile;

import android.app.Activity;
import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fdi.ucm.adapters.PacienteAdapter;
import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Paciente;

public class ListaPacientesFragment extends Fragment {
    private ArrayList<Paciente> pacientes;
    private ArrayList<Medicamento> medicamentos;
    private static final String TAG = "listaPacientes";
    protected RecyclerView mRecyclerView;
    protected PacienteAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    private static final String ARG_PACIENTES= "pacientes";
    private static final String ARG_MEDICAMENTOS= "medicamentos";

    public static ListaPacientesFragment newInstance(ArrayList<Paciente> pacientes, ArrayList<Medicamento> medicamentos) {
        ListaPacientesFragment fragment = new ListaPacientesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PACIENTES, pacientes);
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
    //Carga de los datos pasados por instacia
    private void cargarDatos()
    {
        final Bundle args = getArguments();
        medicamentos=args.getParcelableArrayList(ARG_MEDICAMENTOS);
        pacientes=args.getParcelableArrayList(ARG_PACIENTES);
    }
}
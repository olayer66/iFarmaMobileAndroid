package fdi.ucm.ifarmamobile;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fdi.ucm.adapters.TratamientoPacienteAdapter;
import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Paciente;
import fdi.ucm.model.Tratamiento;


public class TratamientoPacienteFragment extends Fragment {

    private static final String ARG_PACIENTE = "paciente";
    private static final String ARG_MEDICAMENTOS = "medicamentos";

    private Paciente pac;
    private ArrayList<Medicamento> medi;
    ArrayList<Tratamiento> tratamiento;
    protected RecyclerView mRecyclerView;
    protected TratamientoPacienteAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    public TratamientoPacienteFragment() {
        // Required empty public constructor
    }

    public static TratamientoPacienteFragment newInstance(Paciente paciente, ArrayList<Medicamento> medicamentos) {
        TratamientoPacienteFragment fragment = new TratamientoPacienteFragment();
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
        final View view =  inflater.inflate(R.layout.fragment_tratamiento_paciente, container, false);

        final Bundle args = getArguments();
        Paciente paciente= args.getParcelable(ARG_PACIENTE);
        tratamiento = paciente.getTratamiento();

        //cardView del tratamiento
        Activity activity=getActivity();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvPerfilTratamientoMedico);
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<Tratamiento> tr= new ArrayList<>(tratamiento);
        if(tr==null)
            tr= new ArrayList<>();
        mAdapter = new TratamientoPacienteAdapter(tr,paciente);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}

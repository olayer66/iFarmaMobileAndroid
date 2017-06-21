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
    private List<Paciente> pacientes;
    private ArrayList<Medicamento> medicamentos;
    private static final String TAG = "listaPacientes";
    protected RecyclerView mRecyclerView;
    protected PacienteAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        traerPacientes();
        cargarListaMedicamentos();
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
    private void traerPacientes()
    {
        pacientes= Propiedades.getInstance().getMedico().getListaPacientes();
    }
    private ArrayList<Tratamiento> crearTratamiento()
    {
        ArrayList<Tratamiento> tratamientos=new ArrayList<>();

        return tratamientos;
    }
    private void cargarListaMedicamentos()
    {
        medicamentos= new ArrayList<>();
        medicamentos.add(new Medicamento(Long.parseLong("0"),"Viralex","El antivirus definivo","AvastFarma",Double.parseDouble("12")));
        medicamentos.add(new Medicamento(Long.parseLong("0"),"Xor","Solo apto para mojitas","AvastFarma",Double.parseDouble("2")));
        medicamentos.add(new Medicamento(Long.parseLong("0"),"Agrilea","Jalea para brutos","AvastFarma",Double.parseDouble("10")));
        medicamentos.add(new Medicamento(Long.parseLong("0"),"Viagra","Toma Salami","AvastFarma",Double.parseDouble("54")));
        medicamentos.add(new Medicamento(Long.parseLong("0"),"Zumbon","Como viagra pero falso","AvastFarma",Double.parseDouble("10")));
    }
}
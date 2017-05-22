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
import fdi.ucm.adapters.PacienteAdapter;
import fdi.ucm.model.Usuario;

public class ListaPacientesFragment extends Fragment {
    private List<Usuario> pacientes;
    private static final String TAG = "listaPacientes";
    protected RecyclerView mRecyclerView;
    protected PacienteAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        traerMensajes();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lista_pacientes, container, false);
        rootView.setTag(TAG);
        Activity activity=getActivity();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvPacientes);
        mLayoutManager = new LinearLayoutManager(activity);
        mAdapter = new PacienteAdapter(pacientes,activity);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    //Carga con volley los mensajes desde la BBDD
    private void traerMensajes()
    {
        pacientes = new ArrayList<>();
        pacientes.add(new Usuario(Long.parseLong("1"),"Paco","Perez Andaluz","234324554","paco@algo.com"));
        pacientes.add(new Usuario(Long.parseLong("2"),"Ana","Martinez Santos","91563887","ana@algo.com"));
        pacientes.add(new Usuario(Long.parseLong("3"),"Jose Maria"," Gutierrez Sas","635874415","jose_maria@algo.com"));
        pacientes.add(new Usuario(Long.parseLong("4"),"Arturo","Marino Quintana","689257442","arturito@algo.com"));
    }
}
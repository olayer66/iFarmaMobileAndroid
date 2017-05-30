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
        Medico medico= new Medico(Long.parseLong("1"),"Paco","Perez Andaluz","234324554","paco@algo.com","12/14/20004","C.S. Acacias");
        ArrayList<Tratamiento> tratamientos= crearTratamiento();
        ArrayList<Tratamiento> tratVacio= new ArrayList<>();
        pacientes = new ArrayList<>();
        pacientes.add(new Paciente(Long.parseLong("1"),"Paco","Perez Andaluz","234324554","paco@algo.com","C/ falsa 123","Madrid","28045","Madrid","Madrid",medico,tratamientos));
        pacientes.add(new Paciente(Long.parseLong("2"),"Ana","Sanchez Gorfo","234324554","ana@algo.com","C/ falsa 123","Madrid","28045","Madrid","Madrid",medico,tratVacio));
        pacientes.add(new Paciente(Long.parseLong("3"),"Antonio","Perez Sanchez","234324554","antonio@algo.com","C/ falsa 123","Madrid","28045","Madrid","Madrid",medico,tratVacio));
        pacientes.add(new Paciente(Long.parseLong("4"),"Juana","Perez Sanchez","234324554","juana@algo.com","C/ falsa 123","Madrid","28045","Madrid","Madrid",medico,tratamientos));
    }
    private ArrayList<Tratamiento> crearTratamiento()
    {
        ArrayList<Tratamiento> tratamientos=new ArrayList<>();
        Medicamento medicamento= new Medicamento(Long.parseLong("1"),"Tranquinol","Duerme como un liron","TontoFarma",Double.parseDouble("13"));
        Medico medico= new Medico(Long.parseLong("1"),"Paco","Perez Andaluz","234324554","paco@algo.com","12/14/20004","C.S. Acacias");
        Paciente pac=new Paciente(Long.parseLong("1"),"Paco","Perez Andaluz","234324554","paco@algo.com","C/ falsa 123","Madrid","28045","Madrid","Madrid",medico,tratamientos);
        tratamientos.add(new Tratamiento(Long.parseLong("1"),pac,medicamento,"12/05/2017","12/08/2017",0,8,2));
        tratamientos.add(new Tratamiento(Long.parseLong("2"),pac,medicamento,"12/05/2017","12/08/2017",0,8,2));
        tratamientos.add(new Tratamiento(Long.parseLong("3"),pac,medicamento,"12/05/2017","12/08/2017",0,8,2));
        tratamientos.add(new Tratamiento(Long.parseLong("4"),pac,medicamento,"12/05/2017","12/08/2017",0,8,2));
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
package fdi.ucm.ifarmamobile;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fdi.ucm.adapters.MensajeAdapter;
import fdi.ucm.adapters.TratamientoMedicoAdapter;
import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Tratamiento;
import fdi.ucm.model.Usuario;

import static android.R.attr.id;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetallePacienteFragment extends Fragment {
    private List<Tratamiento> tratamientos;
    private Usuario datosPaciente;
    private static final String TAG = "detallePaciente";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
    private OnFragmentInteractionListener mListener;
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected TratamientoMedicoAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    private TextView nombre;
    private TextView telefono;
    private TextView correo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        traerDatos();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detalle_paciente, container, false);

        nombre=(TextView) rootView.findViewById(R.id.detallePacienteNombre);
        correo=(TextView) rootView.findViewById(R.id.detallePacienteCorreo);
        telefono=(TextView) rootView.findViewById(R.id.detallePacienteTelefono);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvTratamientoMedico);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        //Añadimos el TAG de la vista
        rootView.setTag(TAG);
        //Cargamos los datos del paciente en la vista
        cargarDatosPaciente();

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mAdapter = new TratamientoMedicoAdapter(tratamientos);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType =LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }
    //Carga con volley los mensajes desde la BBDD
    private void traerDatos()
    {
        tratamientos= new ArrayList<>();
        Long id=Long.parseLong("1");
        Medicamento med= new Medicamento(id,"Lexatin","Caga rapido, caga fuerte","Cinfa",Double.parseDouble("2"));
        Date fecha= new Date();

        datosPaciente=new Usuario(id,"paco","perez","234324554","paco@algo.com");
        tratamientos.add(new Tratamiento(Long.parseLong("1"),med,fecha,0,8,1));
        tratamientos.add(new Tratamiento(Long.parseLong("2"),med,fecha,0,12,1));
        tratamientos.add(new Tratamiento(Long.parseLong("3"),med,fecha,0,24,2));
        tratamientos.add(new Tratamiento(Long.parseLong("4"),med,fecha,0,6,1));

    }
    private void cargarDatosPaciente()
    {
        nombre.setText(datosPaciente.getApellidos()+","+datosPaciente.getNombre());
        telefono.setText(datosPaciente.getTelefono());
        correo.setText(datosPaciente.getTelefono());
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

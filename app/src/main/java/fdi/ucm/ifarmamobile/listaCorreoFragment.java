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
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import fdi.ucm.adapters.MensajeAdapter;
import fdi.ucm.model.Mensaje;
import fdi.ucm.model.Usuario;



public class listaCorreoFragment extends Fragment {
    private List<Mensaje> mensajes;
    private static final String TAG = "listaCorreo";
    protected RecyclerView mRecyclerView;
    protected MensajeAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        traerMensajes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lista_correo, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvCorreo);

        mAdapter = new MensajeAdapter(mensajes);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }


    //Carga con volley los mensajes desde la BBDD
    private void traerMensajes()
    {
        Long id=Long.parseLong("1");
        Usuario rem=new Usuario(id,"paco","perez","234324554","paco@algo.com");
        Usuario emi=new Usuario(id,"Ana","Sanchez","152356677","ana@algo.com");
        mensajes = new ArrayList<>();
        mensajes.add(new Mensaje(Long.parseLong("3"),"prueba3",rem,emi,"esto es una prueba de mensaje",false,"19/10/2017"));
        mensajes.add(new Mensaje(Long.parseLong("4"),"prueba4",rem,emi,"esto es una prueba de mensaje",false,"27/02/2017"));
        mensajes.add(new Mensaje(Long.parseLong("1"),"prueba1",rem,emi,"esto es una prueba de mensaje",true,"20/10/2017"));
        mensajes.add(new Mensaje(Long.parseLong("2"),"prueba2",rem,emi,"esto es una prueba de mensaje",true,"20/09/2017"));
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

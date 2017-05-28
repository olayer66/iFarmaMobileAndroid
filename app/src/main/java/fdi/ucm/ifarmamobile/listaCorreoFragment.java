package fdi.ucm.ifarmamobile;

import android.app.Activity;
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
import android.widget.ImageButton;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import fdi.ucm.adapters.MensajeAdapter;
import fdi.ucm.model.Mensaje;
import fdi.ucm.model.Usuario;

import static android.R.attr.id;
import static android.R.attr.onClick;


public class listaCorreoFragment extends Fragment {
    private List<Mensaje> mensajes;
    private static final String TAG = "listaCorreo";
    protected RecyclerView mRecyclerView;
    protected MensajeAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ImageButton mBotonAct;
    protected View.OnClickListener mOnClick;


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
        mOnClick= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case  R.id.listaCorreoActualizar: {
                        updateMensajes();
                        break;
                    }
                }
            }
        };
        mBotonAct=(ImageButton) rootView.findViewById(R.id.listaCorreoActualizar);
        mBotonAct.setOnClickListener(mOnClick);
        Activity activity=getActivity();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvCorreo);
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);
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
    //actualiza ña lista de mensajes
    private void updateMensajes()
    {
        Usuario rem=new Usuario(id,"paco","perez","234324554","paco@algo.com");
        Usuario emi=new Usuario(id,"Ana","Sanchez","152356677","ana@algo.com");
        mensajes.add(new Mensaje(Long.parseLong("5"),"prueba5",rem,emi,"esto es una prueba de mensaje",false,"19/10/2017"));
        mensajes.add(new Mensaje(Long.parseLong("5"),"prueba6",rem,emi,"esto es una prueba de mensaje",false,"19/10/2017"));
        mensajes.add(new Mensaje(Long.parseLong("5"),"prueba7",rem,emi,"esto es una prueba de mensaje",false,"19/10/2017"));

        mAdapter.notifyDataSetChanged();
    }
}

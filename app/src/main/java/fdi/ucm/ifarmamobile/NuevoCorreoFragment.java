package fdi.ucm.ifarmamobile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fdi.ucm.model.Usuario;


public class NuevoCorreoFragment extends Fragment {

    private static final String ARG_REMITENTE = "remitente";
    private static final String ARG_EMISOR = "emisor";

    private Usuario remitente;
    private Usuario emisor;

    public static NuevoCorreoFragment newInstance(Usuario remitente, Usuario emisor) {
        NuevoCorreoFragment fragment = new NuevoCorreoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_REMITENTE, remitente);
        args.putParcelable(ARG_EMISOR,emisor);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            remitente = getArguments().getParcelable(ARG_REMITENTE);
            emisor= getArguments().getParcelable(ARG_EMISOR);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_nuevo_correo, container, false);
        // Inflate the layout for this fragment

        return view;
    }

}

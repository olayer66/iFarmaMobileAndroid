package fdi.ucm.ifarmamobile;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fdi.ucm.model.Usuario;


public class NuevoCorreoFragment extends Fragment {

    private static final String ARG_REMITENTE = "remitente";
    private static final String ARG_EMISOR = "emisor";

    private Usuario remitente;
    private Usuario emisor;

    private goBack mListener;

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
        Button enviar=(Button) view.findViewById(R.id.nuevoCorreoEnviar);

        final Bundle bundle;
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
               if(enviarCorreo())
               {
                   mListener=(goBack) v.getContext();
                   AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                   builder.setMessage("Mensaje enviado correctamente.")
                           .setTitle("Enviado")
                           .setCancelable(false)
                           .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {
                                   dialog.cancel();
                                   mListener.goBack();
                               }
                           });
                   AlertDialog alert = builder.create();
                   alert.show();
               }
               else
               {
                   AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                   builder.setMessage("Error al enviar el mensaje, vuelva a intentarlo.")
                           .setTitle("Error")
                           .setCancelable(false)
                           .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {
                                   dialog.cancel();
                               }
                           });
                   AlertDialog alert = builder.create();
                   alert.show();
               }
            }
        });

        return view;
    }
    //Envia el mensaje a la BBDD
    protected boolean enviarCorreo()
    {
        return true;
    }
    //Envia atras
    public interface goBack
    {
        public void goBack();
    }
}

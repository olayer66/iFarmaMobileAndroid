package fdi.ucm.ifarmamobile;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

import fdi.ucm.model.Mensaje;
import fdi.ucm.model.Usuario;

import static fdi.ucm.ifarmamobile.R.string.asunto;
import static fdi.ucm.ifarmamobile.R.string.mensaje;


public class NuevoCorreoFragment extends Fragment {

    private static final String ARG_REMITENTE = "remitente";
    private static final String ARG_DESTINATARIO = "destinatario";

    private Usuario remitente;
    private Usuario destinatario;

    private goBackCorreo mListener;
    private EditText asunto;
    private EditText mensaje;

    public static NuevoCorreoFragment newInstance(Usuario remitente, Usuario destinatario) {
        NuevoCorreoFragment fragment = new NuevoCorreoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_REMITENTE, remitente);
        args.putParcelable(ARG_DESTINATARIO,destinatario);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            remitente = getArguments().getParcelable(ARG_REMITENTE);
            destinatario= getArguments().getParcelable(ARG_DESTINATARIO);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_nuevo_correo, container, false);
        asunto=(EditText) view.findViewById(R.id.nuevoCorreoAsunto);
        mensaje=(EditText) view.findViewById(R.id.nuevoCorreoMensaje);
        final Button enviar=(Button) view.findViewById(R.id.nuevoCorreoEnviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mListener = (goBackCorreo) v.getContext();
                int correcto=validarMensaje();
                if (correcto == 0) {
                    if (enviarCorreo(crearMensaje())) {
                        cargarDialog(v.getContext(),"Enviado","Se ha enviado el mensaje");
                        mListener.goBackCorreo();
                    } else
                        cargarDialog(v.getContext(),"Error","No se ha podido enviar el mensaje, vuelva a intentarlo");
                }
                else
                {
                    switch (correcto)
                    {
                        case 1:
                            cargarDialog(v.getContext(),"Error","El asunto dado no es valido");
                            break;
                        case 2:
                            cargarDialog(v.getContext(),"Error","El mensaje dado no es valido");
                            break;
                        default:
                            cargarDialog(v.getContext(),"Error","Error no especificado");
                    }
                }
            }
        });
        return view;
    }
    //Valida los campos del mensaje (0=Correcto,1=Asunto no valido,2=Mensaje no valido)
    private int validarMensaje()
    {
        String asu= asunto.getText().toString();
        String men=mensaje.getText().toString();
        if(!asu.equals(""))
        {
            if(!men.equals(""))
            {
                return 0;
            }
            else
                return 2;
        }
        else
            return 1;

    }
    //Crea un mensaje
    private Mensaje crearMensaje()
    {
        final Bundle args=getArguments();
        String asu= asunto.getText().toString();
        String men=mensaje.getText().toString();
        Usuario rem=args.getParcelable(ARG_REMITENTE);
        Usuario des=args.getParcelable(ARG_DESTINATARIO);
        String fecha= getFechaActual();
        return new Mensaje(Long.parseLong("0"),asu,rem,des,men,false,fecha);
    }
    //Devuelve la fecha actual
    private String getFechaActual()
    {
        Calendar cal= Calendar.getInstance();
        int dia=cal.get(Calendar.DAY_OF_MONTH);
        int mes=cal.get(Calendar.MONTH)+1;
        String calculoMes=mes/10==0?("0"+mes):String.valueOf(mes);
        int anio=cal.get(Calendar.YEAR);
        return dia +"/"+ calculoMes + "/" +anio;
    }
    //Crea un dialog con los datos pasados por parametro
    private void cargarDialog(Context c, String titulo, String mensaje)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage(mensaje)
                .setTitle(titulo)
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    //Envia el mensaje a la BBDD
    protected boolean enviarCorreo(Mensaje mensaje)
    {

        return true;
    }
    //Envia atras
    public interface goBackCorreo
    {
         void goBackCorreo();
    }
}

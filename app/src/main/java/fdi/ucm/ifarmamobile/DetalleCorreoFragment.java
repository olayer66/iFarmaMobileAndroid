package fdi.ucm.ifarmamobile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fdi.ucm.model.Tratamiento;
import fdi.ucm.model.Usuario;


public class DetalleCorreoFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ASUNTO = "asunto";
    private static final String ARG_REMITENTE = "remitente";
    private static final String ARG_EMISOR = "emisor";
    private static final String ARG_FECHA = "fecha";
    private static final String ARG_MENSAJE = "mensaje";
    // TODO: Rename and change types of parameters
    private String asunto;
    private Usuario remitente;
    private Usuario emisor;
    private String fecha;
    private String mensaje;

    private OnResponderSelected mListener;

    public DetalleCorreoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param asunto Parameter 1.
     * @param remitente Parameter 2.
     * @param emisor Parameter 3.
     * @param fecha Parameter 4.
     * @param mensaje Parameter 5.
     * @return A new instance of fragment DetalleCorreoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleCorreoFragment newInstance(String asunto, Usuario remitente, Usuario emisor, String fecha, String mensaje) {
        DetalleCorreoFragment fragment = new DetalleCorreoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ASUNTO, asunto);
        args.putParcelable(ARG_REMITENTE, remitente);
        args.putParcelable(ARG_EMISOR,emisor);
        args.putString(ARG_FECHA,fecha);
        args.putString(ARG_MENSAJE,mensaje);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            asunto = getArguments().getString(ARG_ASUNTO);
            remitente = getArguments().getParcelable(ARG_REMITENTE);
            emisor= getArguments().getParcelable(ARG_EMISOR);
            fecha=getArguments().getString(ARG_FECHA);
            mensaje=getArguments().getString(ARG_MENSAJE);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_detalle_correo, container, false);
        final TextView asunto = (TextView) view.findViewById(R.id.DetalleCorreoAsunto);
        final TextView remitente = (TextView) view.findViewById(R.id.DetalleCorreoRemitente);
        final TextView fecha = (TextView) view.findViewById(R.id.DetalleCorreoFecha);
        final TextView mensaje = (TextView) view.findViewById(R.id.DetalleCorreoMensaje);
        final Button responder = (Button) view.findViewById(R.id.DetalleCorreoBotonResp);
        final Bundle args = getArguments();
        Usuario rem=args.getParcelable(ARG_REMITENTE);
        asunto.setText(args.getString(ARG_ASUNTO));
        String remi=rem.getNombre()+" "+rem.getApellidos();
        remitente.setText(remi);
        fecha.setText(args.getString(ARG_FECHA));
        mensaje.setText(args.getString(ARG_MENSAJE));
        responder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener= (OnResponderSelected) v.getContext();
                Usuario remitente=args.getParcelable(ARG_EMISOR);
                Usuario emisor= args.getParcelable(ARG_REMITENTE);
                mListener.OnResponderSelected(remitente,emisor);
            }
        });
        return view;
    }

    public interface OnResponderSelected {
        void OnResponderSelected(Usuario remitente, Usuario emisor);
    }
}

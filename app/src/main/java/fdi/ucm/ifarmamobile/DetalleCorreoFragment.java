package fdi.ucm.ifarmamobile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetalleCorreoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetalleCorreoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleCorreoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ASUNTO = "asunto";
    private static final String ARG_REMITENTE = "remitente";
    private static final String ARG_FECHA = "fecha";
    private static final String ARG_MENSAJE = "mensaje";
    // TODO: Rename and change types of parameters
    private String asunto;
    private String remitente;
    private String fecha;
    private String mensaje;

    private OnFragmentInteractionListener mListener;

    public DetalleCorreoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param asunto Parameter 1.
     * @param remitente Parameter 2.
     * @param fecha Parameter 3.
     * @param mensaje Parameter 4.
     * @return A new instance of fragment DetalleCorreoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleCorreoFragment newInstance(String asunto, String remitente, String fecha, String mensaje) {
        DetalleCorreoFragment fragment = new DetalleCorreoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ASUNTO, asunto);
        args.putString(ARG_REMITENTE, remitente);
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
            remitente = getArguments().getString(ARG_REMITENTE);
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

        final Bundle args = getArguments();
        asunto.setText(args.getString(ARG_ASUNTO));
        remitente.setText(args.getString(ARG_REMITENTE));
        fecha.setText(args.getString(ARG_FECHA));
        mensaje.setText(args.getString(ARG_MENSAJE));
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

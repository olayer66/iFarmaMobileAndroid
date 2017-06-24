package fdi.ucm.ifarmamobile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Paciente;


public class PerfilPacienteFragment extends Fragment {

    private static final String ARG_PACIENTE = "paciente";
    private static final String ARG_MEDICAMENTOS = "medicamentos";


    private Paciente pac;
    private ArrayList<Medicamento> medi;

    private OnFragmentInteractionListener mListener;

    public static PerfilPacienteFragment newInstance(Paciente paciente, ArrayList<Medicamento> medicamentos) {
        PerfilPacienteFragment fragment = new PerfilPacienteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PACIENTE, paciente);
        args.putParcelableArrayList(ARG_MEDICAMENTOS, medicamentos);
        fragment.setArguments(args);
        return fragment;
    }

    public PerfilPacienteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pac = getArguments().getParcelable(ARG_PACIENTE);
            medi = getArguments().getParcelable(ARG_MEDICAMENTOS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_perfil_paciente, container, false);


        final TextView nombre = (TextView) view.findViewById(R.id.detallePacienteNombreCompleto);
        final TextView telefono  = (TextView) view.findViewById(R.id.detallePacienteTelefono);
        final TextView email  = (TextView) view.findViewById(R.id.detallePacienteCorreo);
        final TextView direccion  = (TextView) view.findViewById(R.id.detallePacienteDireccion);
        final TextView ciudad  = (TextView) view.findViewById(R.id.detallePacienteCiudad);
        final TextView codpostal  = (TextView) view.findViewById(R.id.detallePacienteCodPostal);
        final TextView provincia  = (TextView) view.findViewById(R.id.detallePacienteProvincia);
        final TextView comunidadAutonoma  = (TextView) view.findViewById(R.id.detallePacienteComAutonoma);
        final TextView medico  = (TextView) view.findViewById(R.id.detallePacienteMedico);


        nombre.setText(pac.getNombre()+" "+pac.getApellidos());
        telefono.setText(pac.getTelefono());
        email.setText(pac.getEmail());
        direccion.setText(pac.getDireccion());
        ciudad.setText(pac.getCiudad());
        codpostal.setText(pac.getCodPostal());
        provincia.setText(pac.getProvincia());
        comunidadAutonoma.setText(pac.getComAutonoma());
        medico.setText(pac.getMedico().getNombre() + " " + pac.getMedico().getApellidos());


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

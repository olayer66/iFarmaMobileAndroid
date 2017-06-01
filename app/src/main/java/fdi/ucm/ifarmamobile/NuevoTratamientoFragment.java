package fdi.ucm.ifarmamobile;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Paciente;
import fdi.ucm.model.Tratamiento;

import static fdi.ucm.ifarmamobile.R.string.anadir;
import static fdi.ucm.ifarmamobile.R.string.medicamento;
import static fdi.ucm.ifarmamobile.R.string.periodicidad;


/**
 * A simple {@link Fragment} subclass.
 */
public class NuevoTratamientoFragment extends Fragment {

    private static final String TAG = "detallePaciente";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PACIENTE= "paciente";
    private static  final String ARG_MEDICAMENTOS="medicamentos";

    private Spinner medicamento;
    private EditText periodicidad;
    private EditText numPastillas;
    private EditText fechaFin;
    private goBackTratamiento mListener;

    // TODO: Rename and change types of parameters
    private Paciente paciente;
    private List<Medicamento> medicamentos;

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            int dia=myCalendar.get(Calendar.DAY_OF_MONTH);
            int mes=myCalendar.get(Calendar.MONTH)+1;
            String calculoMes=mes/10==0?("0"+mes):String.valueOf(mes);
            int anio=myCalendar.get(Calendar.YEAR);
            fechaFin.setText(dia+"/"+calculoMes+"/"+anio);
        }
    };

    public static NuevoTratamientoFragment newInstance(Paciente paciente, ArrayList<Medicamento> medicamentos) {
        NuevoTratamientoFragment fragment = new NuevoTratamientoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PACIENTE,paciente);
        args.putParcelableArrayList(ARG_MEDICAMENTOS,medicamentos);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paciente=getArguments().getParcelable(ARG_PACIENTE);
            medicamentos= getArguments().getParcelableArrayList(ARG_MEDICAMENTOS);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_nuevo_tratamiento, container, false);
        //Spinner
        medicamento=(Spinner) view.findViewById(R.id.nuevoTratamientoMedicamentos);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_personal, cargarSpinner());
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        medicamento.setAdapter(adapter);
        //EditText
        periodicidad=(EditText) view.findViewById(R.id.nuevoTratamientoPeriodicidad);
        numPastillas= (EditText) view.findViewById(R.id.nuevoTratamientoPastillas);
        fechaFin= (EditText) view.findViewById(R.id.nuevoTratamientoFin);
        fechaFin.setText(getFechaActual());
        //Boton calendario
        ImageButton btnCalendario= (ImageButton) view.findViewById(R.id.nuevoTratamientoCalendario);
        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        //Boton enviar
        Button anadir=(Button) view.findViewById(R.id.nuevoTratamientoAnadir);
        anadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String per= periodicidad.getText().toString();
                String nPas= numPastillas.getText().toString();
                int respValidacion=validarDatos(per,nPas);
                if(respValidacion==0)
                {
                    mListener=(goBackTratamiento) v.getContext();
                    if(enviarDatos(crearTratamiento())) {
                        cargarDialog(v.getContext(),"Enviado","Se ha añadido el tratamiento al paciente");
                        mListener.goBackTratamiento();
                    }
                    else
                        cargarDialog(v.getContext(),"Error","No se ha podido añadir, vuelva a intentarlo");
                }
                else
                {
                    switch (respValidacion)
                    {
                        case 1:
                            cargarDialog(v.getContext(),"Error","La fecha tiene que ser superior a la actual");
                            break;
                        case 2:
                            cargarDialog(v.getContext(),"Error","La periodicidad debe de ser superior a 0 y menor de 24 horas");
                            break;
                        case 3:
                            cargarDialog(v.getContext(),"Error","El numero de pastillas ha de ser superior a 0");
                            break;
                        default:
                    }
                }
            }
        });
        return view;
    }
    //Comprobar los datos de entrada (0=ok,1=Fallo fecha,2=fallo periodicidad,3=Fallo pastillas)
    private int validarDatos(String perio, String nPas) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        int dia=myCalendar.get(Calendar.DAY_OF_MONTH);
        int mes=myCalendar.get(Calendar.MONTH)+1;
        int anio=myCalendar.get(Calendar.YEAR);
        String regexStr = "^[0-9]*$";
        String fecha = dia + "/" + mes + "/" + anio;
        Date fechaTratamiento = null;
        try {
            fechaTratamiento = sdf.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (new Date().before(fechaTratamiento)) {
            if (!perio.equals("")) {
                int p = Integer.parseInt(perio);
                if (p > 0 && p <= 24) {
                    if (!nPas.equals("")) {
                        if(Integer.parseInt(nPas)>0) {
                            return 0;
                        }
                        else
                            return 3;
                    }
                    else
                        return 3;
                } else
                    return 2;
            } else
                return 2;
        } else
            return 1;
    }
    //Crea un Tratamiento
    private Tratamiento crearTratamiento()
    {
        final Bundle args=getArguments();
        Paciente pac=args.getParcelable(ARG_PACIENTE);
        Medicamento selecMed=medicamentos.get((int)medicamento.getSelectedItemId());
        String fechInicio=getFechaActual();
        String fechFin=fechaFin.getText().toString();
        int perio=Integer.parseInt(periodicidad.getText().toString());
        int numPas=Integer.parseInt(numPastillas.getText().toString());;
        return new Tratamiento(Long.parseLong("0"),pac,selecMed,fechInicio,fechFin,numPas,perio,0);
    }
    //Carga lo datos del los medicamentos en la list para el Spinner
    private List<String> cargarSpinner() {
        List<String> nombres= new ArrayList<>();
        Iterator<Medicamento> iter= medicamentos.iterator();
        while (iter.hasNext())
        {
            nombres.add(iter.next().getNombre());
        }
        return nombres;
    }
    //Message dialog
    private void cargarDialog(Context c, String titulo, String mensaje) {
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
    //Devuelve la fecha actual
    private String getFechaActual() {
        Calendar cal= Calendar.getInstance();
        int dia=cal.get(Calendar.DAY_OF_MONTH);
        int mes=cal.get(Calendar.MONTH)+1;
        String calculoMes=mes/10==0?("0"+mes):String.valueOf(mes);
        int anio=cal.get(Calendar.YEAR);
        return dia +"/"+ calculoMes + "/" +anio;
    }
    private boolean enviarDatos(Tratamiento tratamiento)
    {
        return true;
    }
    public interface goBackTratamiento {
        void goBackTratamiento();
    }

}

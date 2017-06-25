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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import fdi.ucm.Propiedades;
import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Paciente;
import fdi.ucm.model.Tratamiento;
import fdi.ucm.volley.Conexion;

import static fdi.ucm.ifarmamobile.R.string.anadir;
import static fdi.ucm.ifarmamobile.R.string.medicamento;
import static fdi.ucm.ifarmamobile.R.string.periodicidad;
import static fdi.ucm.ifarmamobile.R.string.tratamiento;


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
    private Context context;

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
            public void onClick(final View v) {
                String per= periodicidad.getText().toString();
                String nPas= numPastillas.getText().toString();
                int respValidacion=validarDatos(per,nPas);
                if(respValidacion==0)
                {
                    final Bundle args=getArguments();
                    final Paciente paciente=args.getParcelable(ARG_PACIENTE);
                    mListener=(goBackTratamiento) v.getContext();
                    context=v.getContext();
                    final Tratamiento trat=crearTratamiento();
                    enviarDatos(context, trat,paciente.getId(), new OnNuevoTratamientoResp() {
                        @Override
                        public void OnRespuesta(JSONObject response) {
                            try {
                                String error= response.getString("error");
                                String mensaje= response.getString("mensaje");
                                if(error.equals("red")){
                                    cargarDialog(context,context.getString(R.string.mensaje_error_title),context.getString(R.string.tratamiento_anadir_error));
                                    Toast.makeText(context,context.getString(R.string.error_red),Toast.LENGTH_LONG).show();
                                } else {
                                    int posicion= Propiedades.getInstance().getMedico().getListaPacientes().indexOf(paciente);
                                    Propiedades.getInstance().getMedico().getListaPacientes().get(posicion).getTratamiento().add(trat);
                                    cargarDialog(context,context.getString(R.string.mensaje_enviado_title),context.getString(R.string.tratamiento_anadir_ok));
                                    mListener.goBackTratamiento();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                else
                {
                    switch (respValidacion)
                    {
                        case 1:
                            cargarDialog(v.getContext(),context.getString(R.string.mensaje_error_title),context.getString(R.string.tratamiento_anadir_fecha));
                            break;
                        case 2:
                            cargarDialog(v.getContext(),context.getString(R.string.mensaje_error_title),context.getString(R.string.tratamiento_anadir_periodicidad));
                            break;
                        case 3:
                            cargarDialog(v.getContext(),context.getString(R.string.mensaje_error_title),context.getString(R.string.tratamiento_anadir_pastillas));
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
        Medicamento selecMed=medicamentos.get((int)medicamento.getSelectedItemId());
        String fechInicio=getFechaActual();
        String fechFin=fechaFin.getText().toString();
        String perio=periodicidad.getText().toString();
        int numPas=Integer.parseInt(numPastillas.getText().toString());;
        return new Tratamiento(Long.parseLong("0"),selecMed,fechInicio,fechFin,numPas,perio);
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
    private void enviarDatos(final Context context,final Tratamiento tratamiento,long id, final OnNuevoTratamientoResp callback)
    {
        String URL= Conexion.getInstance().getPrefixURL()+"nuevoTratamiento";
        JSONObject request= Conexion.tratamientoToJson(tratamiento, id);
        JsonRequest nuevoMensaje = new JsonObjectRequest(Request.Method.POST,URL, request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.OnRespuesta(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        JSONObject err= new JSONObject();
                        try {
                            err.put("error","red");
                            err.put("mensaje",error.getMessage());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.OnRespuesta(err);
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(nuevoMensaje);

    }
    private interface OnNuevoTratamientoResp{
        void OnRespuesta(JSONObject response);
    }
    public interface goBackTratamiento {
        void goBackTratamiento();
    }

}

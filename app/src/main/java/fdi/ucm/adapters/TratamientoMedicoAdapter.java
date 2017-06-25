package fdi.ucm.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
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

import java.util.ArrayList;

import fdi.ucm.Propiedades;
import fdi.ucm.ifarmamobile.R;
import fdi.ucm.model.Paciente;
import fdi.ucm.model.Tratamiento;
import fdi.ucm.volley.Conexion;

public class TratamientoMedicoAdapter extends RecyclerView.Adapter<TratamientoMedicoAdapter.PersonViewHolder>{

    private ArrayList<Tratamiento> tratamientos;
    private Paciente paciente;
    public TratamientoMedicoAdapter(ArrayList<Tratamiento> tratamientos, Paciente paciente){
        this.tratamientos = tratamientos;
        this.paciente= paciente;

    }
    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView medicamento;
        TextView tomas;
        TextView fechaFin;
        TextView id;
        ImageButton eliminar;
        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvTratamientoMedico);
            medicamento = (TextView)itemView.findViewById(R.id.cardTratamientoMedicoMedicamento);
            fechaFin=(TextView) itemView.findViewById(R.id.cardTratamientoMedicoFechaFin);
            tomas=(TextView) itemView.findViewById(R.id.cardTratamientoMedicoTomas);
            id=(TextView) itemView.findViewById(R.id.cardTratamientoMedicoId);
            eliminar=(ImageButton) itemView.findViewById(R.id.cardTratamientoMedicoBotonEliminar);
        }
    }
    @Override
    public int getItemCount() {
        return tratamientos.size();
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_tratamiento_medico, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }
    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, final int i) {
        personViewHolder.medicamento.setText(tratamientos.get(i).getMedicamento().getNombre());
        String tomas=tratamientos.get(i).getNumDosis()+" cada " +tratamientos.get(i).getPerioicidad();
        personViewHolder.tomas.setText(tomas);
        personViewHolder.fechaFin.setText(tratamientos.get(i).getFechaFin());
        personViewHolder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                eliminarTratamiento(v.getContext(), tratamientos.get(i).getId(), new OnEliminarTratamiento() {
                    @Override
                    public void OnRespuesta(String estado) {
                        if(estado.equals("red"))
                            Toast.makeText(v.getContext(),v.getContext().getString(R.string.error_red),Toast.LENGTH_LONG).show();
                        else{
                            Tratamiento tratamiento= tratamientos.get(i);
                            int posicion=Propiedades.getInstance().getMedico().getListaPacientes().indexOf(paciente);
                            Propiedades.getInstance().getMedico().getListaPacientes().get(posicion).getTratamiento().remove(tratamiento);
                            tratamientos.remove(i);
                            notifyItemRemoved(i);
                            notifyDataSetChanged();
                            Toast.makeText(v.getContext(),v.getContext().getString(R.string.tratamiento_eliminar_ok),Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
        personViewHolder.id.setText(String.valueOf(i));
    }
    private void eliminarTratamiento(final Context context, final long id, final OnEliminarTratamiento callback){
        String URL= Conexion.getInstance().getPrefixURL()+"eliminarTratamiento";
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject request=new JSONObject();
        try {
            request.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonRequest eliminarTratamiento = new JsonObjectRequest(Request.Method.POST,URL, request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.OnRespuesta("ok");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.OnRespuesta("red");
                    }
                }){
        };
        queue.add(eliminarTratamiento);
    }
    private interface OnEliminarTratamiento{
        void OnRespuesta(String estado);
    }
}

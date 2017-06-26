package fdi.ucm.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fdi.ucm.ifarmamobile.R;
import fdi.ucm.model.Mensaje;
import fdi.ucm.model.Usuario;
import fdi.ucm.volley.Conexion;

import static android.R.attr.id;


public class MensajeAdapter  extends RecyclerView.Adapter<MensajeAdapter.PersonViewHolder>{

    int anchoPantalla;
    private List<Mensaje> mensajes;
    private OnCorreoSelected mListener;

    public MensajeAdapter(List<Mensaje> mensajes, int ancho){
        this.mensajes = mensajes;
        anchoPantalla = ancho;
    }
    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView asunto;
        TextView remitente;
        TextView fecha;
        ImageView leido;
        TextView id;
        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvCorreo);
            asunto = (TextView)itemView.findViewById(R.id.cardCorreoAsunto);
            remitente=(TextView) itemView.findViewById(R.id.cardCorreoRemitente);
            fecha=(TextView) itemView.findViewById(R.id.cardCorreoFecha);
            leido=(ImageView) itemView.findViewById(R.id.cardCorreoNuevo);
            id=(TextView) itemView.findViewById(R.id.cardCorreoId);
        }
    }
    @Override
    public int getItemCount() {
        return mensajes.size();
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_correo, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }
    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder,final int i) {

        personViewHolder.asunto.setText(mensajes.get(i).getAsunto());
        //Reajustamos el tamaño si es demasiado grande

        /*if(mensajes.get(i).getAsunto().getMaxWidth() >  anchoPantalla){
            String aux = mensajes.get(i).getAsunto();
            //int number = personViewHolder.asunto.length();
            String asuntoAux = aux.substring(0, personViewHolder.asunto.length()-3) + "...";
            personViewHolder.asunto.setText(asuntoAux);
        }*/

        String remitente=mensajes.get(i).getRemitente().getNombre()+" "+mensajes.get(i).getRemitente().getApellidos();
        personViewHolder.remitente.setText(remitente);
        personViewHolder.fecha.setText(mensajes.get(i).getFecha());
        personViewHolder.id.setText(String.valueOf(i));
        personViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener=(OnCorreoSelected)v.getContext();
                mensajes.get(i).setLeido(true);
                actMensajeLeido(mensajes.get(i).getId(), v.getContext());
                mListener.OnCorreoSelected(mensajes.get(i).getAsunto(),
                                           mensajes.get(i).getRemitente(),
                                           mensajes.get(i).getDestinatario(),
                                           mensajes.get(i).getFecha(),
                                           mensajes.get(i).getMensaje());
            }
        });
        if(!mensajes.get(i).getleido())
        {
            personViewHolder.leido.setVisibility(View.VISIBLE);
        }
    }
    private void actMensajeLeido(final long id , final Context context)
    {
        String URL= Conexion.getInstance().getPrefixURL()+"actMensaje";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String estado=response;
                //Toast.makeText(context,estado,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,context.getString(R.string.error_red),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("id", Long.toString(id));
                return params;
            }
        };
        queue.add(sr);
    }
    public interface OnCorreoSelected {
        void OnCorreoSelected(String asunto, Usuario remitente,Usuario emisor, String fecha, String mensaje);
    }
}

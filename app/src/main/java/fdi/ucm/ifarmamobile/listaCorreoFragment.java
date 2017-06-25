package fdi.ucm.ifarmamobile;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fdi.ucm.Propiedades;
import fdi.ucm.adapters.MensajeAdapter;
import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Mensaje;
import fdi.ucm.model.Paciente;
import fdi.ucm.model.Usuario;
import fdi.ucm.volley.Conexion;

import static android.R.attr.id;
import static android.R.attr.onClick;


public class listaCorreoFragment extends Fragment {
    private List<Mensaje> mensajes;
    private static final String TAG = "listaCorreo";
    protected RecyclerView mRecyclerView;
    protected MensajeAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ImageButton mBotonAct;
    protected View.OnClickListener mOnClick;
    private static final String ARG_MENSAJES="mensajes";
    private static final String ARG_ID="id";

    public static listaCorreoFragment newInstance(ArrayList<Mensaje> mensajes, long id) {
        listaCorreoFragment fragment = new listaCorreoFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_MENSAJES, mensajes);
        args.putLong(ARG_ID,id);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        traerMensajes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lista_correo, container, false);
        rootView.setTag(TAG);
        mOnClick= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case  R.id.listaCorreoActualizar: {
                        actMensajes(v.getContext(), getArguments().getLong(ARG_ID), new OnActMensajes() {
                            @Override
                            public void OnRespuesta(JSONObject response) {
                                try {
                                    String error= response.getString("error");
                                    if(error.equals("red")) {
                                        Toast.makeText(getContext(),getString(R.string.error_red),Toast.LENGTH_LONG).show();
                                    }else {
                                        mensajes = crearListaMensajes(response.getJSONObject("mensajes"));
                                        mAdapter.notifyDataSetChanged();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        break;
                    }
                }
            }
        };
        mBotonAct=(ImageButton) rootView.findViewById(R.id.listaCorreoActualizar);
        mBotonAct.setOnClickListener(mOnClick);
        Activity activity=getActivity();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvCorreo);
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        int ancho = container.getWidth();

        mAdapter = new MensajeAdapter(mensajes, ancho);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
    //Carga con volley los mensajes desde la BBDD
    private void traerMensajes()
    {
        final Bundle args = getArguments();
        mensajes=args.getParcelableArrayList(ARG_MENSAJES);
    }
    //actualiza la lista de mensajes
    private void actMensajes(final Context context, final long id, final OnActMensajes callback)
    {
        String URL= Conexion.getInstance().getPrefixURL()+"actListaMensajes";
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject request=new JSONObject();
        try {
            request.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonRequest ectListaMen = new JsonObjectRequest(Request.Method.POST,URL, request,
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
                }){
        };
        queue.add(ectListaMen);
    }
    //pasa de json a arraylist de mensajes
    private ArrayList<Mensaje> crearListaMensajes(JSONObject entrada)
    {
        ArrayList<Mensaje> listaMen= new ArrayList<>();
        try {
            JSONArray lista= entrada.getJSONArray("mensajes");
            for(int i=0;i<lista.length();i++)
            {
                listaMen.add(Conexion.parserMensaje(lista.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaMen;
    }
    private interface OnActMensajes{
        void OnRespuesta(JSONObject response);
    }
}

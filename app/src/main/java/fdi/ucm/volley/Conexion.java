package fdi.ucm.volley;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fdi.ucm.ifarmamobile.LoginActivity;


public class Conexion {
    private static final String TAG = "NetworkManager";
    private static Conexion instance = null;
    /*------LOCAL--------*/
    private static final String prefixURL = "http://localhost:8080/mobile/";
    /*------SERVIDOR--------*/
    //private static final String prefixURL = "http://container.fdi.ucm.es:20007/mobile";
    //for Volley API
    public RequestQueue requestQueue;

    private Conexion(Context context)
    {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        //other stuf if you need
    }

    public static synchronized Conexion getInstance(Context context)
    {
        if (null == instance)
            instance = new Conexion(context);
        return instance;
    }

    //this is so you don't need to pass context each time
    public static synchronized Conexion getInstance()
    {
        if (null == instance)
        {
            throw new IllegalStateException(Conexion.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    public void loginRequest(final String mUsuario,final String mPassword, final respuestaListener<String> listener)
    {

        final String LOGIN_URL = prefixURL+"inicio";
        StringRequest loginRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            listener.getResult(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("ssoId",mUsuario);
                map.put("password",mPassword);
                return map;
            }
        };
        requestQueue.add(loginRequest);
    }
    public interface respuestaListener<T>
    {
        public void getResult(T object);
    }
}

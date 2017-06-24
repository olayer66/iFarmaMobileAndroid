package fdi.ucm.volley;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Medico;
import fdi.ucm.model.Mensaje;
import fdi.ucm.model.Paciente;
import fdi.ucm.model.Tratamiento;
import fdi.ucm.model.Usuario;

public class Conexion {
    private static final String TAG = "NetworkManager";
    private static Conexion instance = null;
    /*------LOCAL--------*/
    //private static final String prefixURL = "http://localhost:8080/mobile/";
    /*------SERVIDOR--------*/
    private static final String prefixURL = "http://container.fdi.ucm.es:20007/mobile/";

    public static synchronized Conexion getInstance()
    {
        if (null == instance)
            instance = new Conexion();
        return instance;
    }
    /*parsers el JSON de la BBDD a las entitys locales*/
    //JSON a medico
    public static Medico parserMedico(JSONObject entrada, int tipo) {
        try {
            long id= entrada.getLong("id");
            String nombre=entrada.getString("nombre");
            String apellidos= entrada.getString("apellidos");
            String telefono= entrada.getString("telefono");
            String email= entrada.getString("email");
            String numColegiado= entrada.getString("numColMedico");
            String centroTrabajo= entrada.getString("centroTrabajo");
            ArrayList<Paciente> listaPacientes = new ArrayList<>();
            ArrayList<Mensaje> listaMensajes = new ArrayList<>();
            if(tipo==0) {
                JSONArray listaP = entrada.getJSONArray("listaPacientes");
                for (int i = 0; i < listaP.length(); i++) {
                    listaPacientes.add(parserPaciente(listaP.getJSONObject(i),1));
                }
                JSONArray listaM = entrada.getJSONArray("listaMensajes");
                for (int i = 0; i < listaM.length(); i++) {
                    listaMensajes.add(parserMensaje(listaM.getJSONObject(i)));
                }
            }
            Medico medico =new Medico(id,nombre,apellidos,telefono,email,numColegiado,centroTrabajo,listaPacientes,listaMensajes);
            return medico;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    //JSON a paciente
    public static Paciente parserPaciente(JSONObject entrada, int tipo) {
        try {
            long id= entrada.getLong("id");
            String nombre=entrada.getString("nombre");
            String apellidos= entrada.getString("apellidos");
            String telefono= entrada.getString("telefono");
            String email= entrada.getString("email");
            String direccion= entrada.getString("direccion");
            String ciudad= entrada.getString("ciudad");
            String codPostal= entrada.getString("codPostal");
            String provincia= entrada.getString("provincia");
            String conAutonoma= entrada.getString("comAutonoma");
            ArrayList<Mensaje> listaMensajes = new ArrayList<>();
            ArrayList<Tratamiento> tratamientos = new ArrayList<>();
            Medico medicoCabecera=null;
            if(tipo==0) {
                medicoCabecera = parserMedico(entrada.getJSONObject("medCabecera"),1);
                JSONArray listaM = entrada.getJSONArray("listaMensajes");
                for (int i = 0; i < listaM.length(); i++) {
                    listaMensajes.add(parserMensaje(listaM.getJSONObject(i)));
                }
                JSONArray listaT = entrada.getJSONArray("tratamientos");
                for (int i = 0; i < listaT.length(); i++) {
                    tratamientos.add(parserTratamiento(listaT.getJSONObject(i)));
                }
            }
            return new Paciente(id,nombre,apellidos,telefono,email,direccion,ciudad,codPostal,provincia,conAutonoma,medicoCabecera,tratamientos,listaMensajes);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    //JSON a mensaje
    public static Mensaje parserMensaje(JSONObject entrada) {
        try {
            Long id = entrada.getLong("id");
            String asunto= entrada.getString("asunto");
            String mensaje=entrada.getString("mensaje");
            String fecha= entrada.getString("mensaje");
            boolean leido= entrada.getBoolean("leido");
            Usuario remitente= parserUsuario(entrada.getJSONObject("remitente"));
            Usuario destinatario=parserUsuario(entrada.getJSONObject("destinatario"));
            return new Mensaje(id,asunto,remitente,destinatario,mensaje,leido,fecha);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    //JSON a tratamiento
    public static Tratamiento parserTratamiento(JSONObject entrada) {
        try {
            long id= entrada.getLong("id");
            Medicamento medicamento= parserMedicamento(entrada.getJSONObject("medicamento"));
            String fechaInicio= entrada.getString("fechaInicio");
            String fechaFin= entrada.getString("fechaFin");
            String periodicidad= entrada.getString("periodicidad");
            int numDosis= entrada.getInt("numDosis");
            return new Tratamiento(id,medicamento,fechaInicio,fechaFin,numDosis,Integer.parseInt(periodicidad));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    //JSON a usuario
    public static Usuario parserUsuario(JSONObject entrada) {
        try{
            long id= entrada.getLong("id");
            String nombre=entrada.getString("nombre");
            String apellidos= entrada.getString("apellidos");
            String telefono= entrada.getString("telefono");
            String email= entrada.getString("email");
            return new Usuario(id,nombre,apellidos,telefono,email);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    //JSON a Medicamento
    public static Medicamento parserMedicamento(JSONObject entrada) {
        try{
            long id= entrada.getLong("id");
            String nombre=entrada.getString("nombre");
            String descripcion= entrada.getString("descripcion");
            String laboratorio= entrada.getString("laboratorio");
            Double precio= entrada.getDouble("precio");
            return new Medicamento(id,nombre,descripcion,laboratorio,precio);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPrefixURL() {
        return prefixURL;
    }
}

package fdi.ucm.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class Medico implements Parcelable {
    //estandar
    private long id;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    //Especificos
    private String numColegiado;
    private String centroTrabajo;
    //ArrayLista de pacientes
    private ArrayList<Paciente> listaPacientes;

    private ArrayList<Mensaje> listaMensajes;
    public  Medico(long id,String nombre,String apellidos, String telefono, String email,String numColegiado,String centroTrabajo, ArrayList<Paciente>listaPacientes,ArrayList<Mensaje>listaMensajes)
    {
        this.id=id;
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.telefono=telefono;
        this.email=email;

        this.numColegiado=numColegiado;
        this.centroTrabajo=centroTrabajo;

        this.listaPacientes=listaPacientes;
        this.listaMensajes=listaMensajes;
    }

    protected Medico(Parcel in) {
        id = in.readLong();
        nombre = in.readString();
        apellidos = in.readString();
        telefono = in.readString();
        email = in.readString();
        numColegiado = in.readString();
        centroTrabajo = in.readString();
        listaPacientes= in.readArrayList(Paciente.class.getClassLoader());
        listaMensajes= in.readArrayList(Mensaje.class.getClassLoader());
    }

    public static final Creator<Medico> CREATOR = new Creator<Medico>() {
        @Override
        public Medico createFromParcel(Parcel in) {
            return new Medico(in);
        }

        @Override
        public Medico[] newArray(int size) {
            return new Medico[size];
        }
    };

    public long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellidos() {
        return apellidos;
    }
    public String getTelefono() {
        return telefono;
    }

    public String getNumColegiado() {
        return numColegiado;
    }
    public String getCentroTrabajo() {
        return centroTrabajo;
    }

    public ArrayList<Paciente> getListaPacientes() {
        return listaPacientes;
    }
    public ArrayList<Mensaje> getListaMensajes() {
        return listaMensajes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeString(email);
        dest.writeString(telefono);
        dest.writeString(numColegiado);
        dest.writeString(centroTrabajo);
        dest.writeList(listaPacientes);
        dest.writeList(listaMensajes);
    }
    public void readFromParcel(Parcel in) {
        id = in.readLong();
        nombre = in.readString();
        apellidos = in.readString();
        email = in.readString();
        telefono = in.readString();
        numColegiado= in.readString();
        centroTrabajo=in.readString();
        listaPacientes=in.readArrayList(Paciente.class.getClassLoader());
        listaMensajes= in.readArrayList(Mensaje.class.getClassLoader());
    }
}

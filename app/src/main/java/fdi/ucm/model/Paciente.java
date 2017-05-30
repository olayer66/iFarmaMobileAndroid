package fdi.ucm.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by joset on 21/05/2017.
 */

public class Paciente implements Parcelable {
    //estandar
    private long id;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    //Especificos
    private String direccion;
    private String ciudad;
    private String codPostal;
    private String provincia;
    private String comAutonoma;
    private Medico medico;
    private ArrayList<Tratamiento> tratamiento;

    public Paciente(long id,String nombre,String apellidos, String telefono, String email,String direccion,String ciudad,String codPostal, String provincia,String comAutonoma,Medico medico,ArrayList<Tratamiento> tratamiento)
    {
        this.id=id;
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.telefono=telefono;
        this.email=email;

        this.direccion=direccion;
        this.ciudad=ciudad;
        this.codPostal=codPostal;
        this.provincia=provincia;
        this.comAutonoma=comAutonoma;
        this.medico=medico;
        this.tratamiento=tratamiento;
    }

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

    public String getDireccion() {
        return direccion;
    }
    public String getCiudad() {
        return ciudad;
    }
    public String getCodPostal() {
        return codPostal;
    }
    public String getProvincia() {
        return provincia;
    }
    public String getComAutonoma() {
        return comAutonoma;
    }
    public Medico getMedico() {
        return medico;
    }
    public ArrayList<Tratamiento> getTratamiento() {
        return tratamiento;
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
        dest.writeString(direccion);
        dest.writeString(ciudad);
        dest.writeString(codPostal);
        dest.writeString(provincia);
        dest.writeString(comAutonoma);
        dest.writeParcelable(medico,flags);
        dest.writeList(tratamiento);
    }
    public void readFromParcel(Parcel in) {
        id = in.readLong();
        nombre= in.readString();
        apellidos= in.readString();
        email= in.readString();
        telefono= in.readString();
        direccion= in.readString();
        ciudad= in.readString();
        codPostal= in.readString();
        provincia= in.readString();
        comAutonoma= in.readString();
        medico=in.readParcelable(Medico.class.getClassLoader());
        tratamiento=in.readArrayList(Tratamiento.class.getClassLoader());
    }
}

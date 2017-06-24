package fdi.ucm.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joset on 17/05/2017.
 */

public class Usuario implements Parcelable {
    private long idUsuario;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;

    public Usuario( long ID,String nombre,String apellidos, String telefono, String email)
    {
        this.idUsuario=ID;
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.telefono=telefono;
        this.email=email;
    }
    public String getEmail() {
        return email;
    }

    public long getIdUsuario() {
        return idUsuario;
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



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idUsuario);
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeString(telefono);
        dest.writeString(email);

    }
    public void readFromParcel(Parcel in) {
        idUsuario=in.readLong();
        nombre= in.readString();
        apellidos=in.readString();
        telefono=in.readString();
        email=in.readString();
    }
}

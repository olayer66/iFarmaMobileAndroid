package fdi.ucm.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joset on 21/05/2017.
 */

public class Medicamento implements Parcelable{
    private long id;
    private String nombre;
    private String descripcion;
    private String laboratorio;
    private double precio;

    public Medicamento(long id, String nombre,String descripcion, String laboratorio, double precio)
    {
        this.id=id;
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.laboratorio=laboratorio;
        this.precio=precio;
    }

    public long getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getLaboratorio() {
        return laboratorio;
    }
    public double getPrecio() {
        return precio;
    }

    public Medicamento(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Medicamento> CREATOR = new Parcelable.Creator<Medicamento>() {
        public Medicamento createFromParcel(Parcel in) {
            return new Medicamento(in);
        }

        public Medicamento[] newArray(int size) {

            return new Medicamento[size];
        }

    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(nombre);
        dest.writeString(descripcion);
        dest.writeString(laboratorio);
        dest.writeDouble(precio);
    }
    public void readFromParcel(Parcel in) {
        id = in.readLong();
        nombre = in.readString();
        descripcion=in.readString();
        laboratorio=in.readString();;
        precio=in.readDouble();
    }
}

package fdi.ucm.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class Tratamiento implements Parcelable {
    private long id;
    private Medicamento medicamento;
    private String fechaInicio;
    private String fechaFin;
    private int numDosis;
    private String perioicidad;
    public Tratamiento (long id,Medicamento medicamento, String fechaInicio,String fechaFin, int numDosis,String perioicidad)
    {
        this.id=id;
        this.medicamento=medicamento;
        this.fechaInicio=fechaInicio;
        this.fechaFin=fechaFin;
        this.numDosis=numDosis;
        this.perioicidad=perioicidad;
    }

    public long getId() {
        return id;
    }
    public Medicamento getMedicamento() {
        return medicamento;
    }
    public String getFechaInicio(){return fechaInicio;}
    public String getFechaFin() {
        return fechaFin;
    }
    public int getNumDosis() {
        return numDosis;
    }
    public String getPerioicidad() {
        return perioicidad;
    }

    public Tratamiento(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Tratamiento> CREATOR = new Parcelable.Creator<Tratamiento>() {
        public Tratamiento createFromParcel(Parcel in) {
            return new Tratamiento(in);
        }

        public Tratamiento[] newArray(int size) {

            return new Tratamiento[size];
        }

    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeParcelable(medicamento,flags);
        dest.writeString(fechaInicio);
        dest.writeString(fechaFin);
        dest.writeInt(numDosis);
        dest.writeString(perioicidad);
    }
    public void readFromParcel(Parcel in) {
        id = in.readLong();
        medicamento = in.readParcelable(Medicamento.class.getClassLoader());
        fechaFin = in.readString();
        fechaInicio=in.readString();
        numDosis=in.readInt();
        perioicidad=in.readString();
    }
}

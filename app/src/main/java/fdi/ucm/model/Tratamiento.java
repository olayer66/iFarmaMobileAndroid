package fdi.ucm.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by joset on 21/05/2017.
 */

public class Tratamiento implements Parcelable {
    private long id;
    private Medicamento medicamento;
    private String fechaFinTratamiento;
    private int numDosis;
    private int perioicidad;
    private int numDosisDia;
    public Tratamiento (long id,Medicamento medicamento, String fechaFinTratamiento, int numDosis,int perioicidad,int numDosisDia)
    {
        this.id=id;
        this.medicamento=medicamento;
        this.fechaFinTratamiento=fechaFinTratamiento;
        this.numDosis=numDosis;
        this.perioicidad=perioicidad;
        this.numDosisDia=numDosisDia;
    }

    public long getId() {
        return id;
    }
    public Medicamento getMedicamento() {
        return medicamento;
    }
    public String getFechaFinTratamiento() {
        return fechaFinTratamiento;
    }
    public int getNumDosis() {
        return numDosis;
    }
    public int getPerioicidad() {
        return perioicidad;
    }
    public int getNumDosisDia() {
        return numDosisDia;
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
        dest.writeString(fechaFinTratamiento);
        dest.writeInt(numDosis);
        dest.writeInt(perioicidad);
        dest.writeInt(numDosisDia);
    }
    public void readFromParcel(Parcel in) {
        id = in.readLong();
        medicamento = in.readParcelable(Medicamento.class.getClassLoader());
        fechaFinTratamiento = in.readString();
        numDosis=in.readInt();
        perioicidad=in.readInt();
        numDosisDia=in.readInt();
    }
}

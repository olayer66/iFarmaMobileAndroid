package fdi.ucm.model;

import java.util.Date;

/**
 * Created by joset on 21/05/2017.
 */

public class Tratamiento {
    private long id;
    private Medicamento medicamento;
    private Date fechaFinTratamiento;
    private int numDosis;
    private int perioicidad;
    private int numDosisDia;
    public Tratamiento (long id,Medicamento medicamento, Date fechaFinTratamiento, int numDosis,int perioicidad,int numDosisDia)
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
    public Date getFechaFinTratamiento() {
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
}

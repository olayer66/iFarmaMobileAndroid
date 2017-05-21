package fdi.ucm.model;

/**
 * Created by joset on 21/05/2017.
 */

public class Medico {
    private long id;
    private String numColegiado;
    private String centroTrabajo;
    public void Medico(long id,String numColegiado,String centroTrabajo)
    {
        this.id=id;
        this.numColegiado=numColegiado;
        this.centroTrabajo=centroTrabajo;
    }

    public long getId() {
        return id;
    }

    public String getNumColegiado() {
        return numColegiado;
    }

    public String getCentroTrabajo() {
        return centroTrabajo;
    }
}

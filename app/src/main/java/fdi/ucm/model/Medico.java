package fdi.ucm.model;

import static android.R.attr.id;

/**
 * Created by joset on 21/05/2017.
 */

public class Medico {
    //estandar
    private long id;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    //Especificos
    private String numColegiado;
    private String centroTrabajo;
    public  Medico(long id,String nombre,String apellidos, String telefono, String email,String numColegiado,String centroTrabajo)
    {
        this.id=id;
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.telefono=telefono;
        this.email=email;

        this.numColegiado=numColegiado;
        this.centroTrabajo=centroTrabajo;
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

    public String getNumColegiado() {
        return numColegiado;
    }
    public String getCentroTrabajo() {
        return centroTrabajo;
    }
}

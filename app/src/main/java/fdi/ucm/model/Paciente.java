package fdi.ucm.model;

import java.util.ArrayList;

/**
 * Created by joset on 21/05/2017.
 */

public class Paciente {
    private long id;
    private String direccion;
    private String ciudad;
    private String codPostal;
    private String provincia;
    private String comAutonoma;
    private Usuario medico;
    private ArrayList<Tratamiento> tratamiento;

    public void Paciente(long id,String direccion,String ciudad,String codPostal, String provincia,String comAutonoma,Usuario medico,ArrayList<Tratamiento> tratamiento)
    {
        this.id=id;
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
    public Usuario getMedico() {
        return medico;
    }
    public ArrayList<Tratamiento> getTratamiento() {
        return tratamiento;
    }
}

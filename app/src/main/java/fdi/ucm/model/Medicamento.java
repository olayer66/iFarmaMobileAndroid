package fdi.ucm.model;

/**
 * Created by joset on 21/05/2017.
 */

public class Medicamento {
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
}

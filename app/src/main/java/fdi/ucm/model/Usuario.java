package fdi.ucm.model;

/**
 * Created by joset on 17/05/2017.
 */

public class Usuario {
    private Long idUsuario;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;

    public Usuario( Long ID,String nombre,String apellidos, String telefono, String email)
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

    public Long getIdUsuario() {
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
}

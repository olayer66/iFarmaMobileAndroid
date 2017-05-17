package fdi.ucm.model;

import static android.R.attr.name;

/**
 * Created by joset on 16/05/2017.
 */

public class Mensaje {
    private String asunto;
    private Usuario remitente;
    private Usuario mensaje;
    private boolean leido;
    public Mensaje(String asunto, Usuario remitente, Usuario mensaje, boolean leido) {
        this.asunto=asunto;
        this.remitente=remitente;
        this.mensaje=mensaje;
        this.leido=leido;
    }
    public String getAsunto()
    {
        return asunto;
    }
    public Usuario getRemitente()
    {
        return remitente;
    }
    public Usuario getMensaje()
    {
        return mensaje;
    }
    public boolean getleido()
    {
        return leido;
    }
}
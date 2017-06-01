package fdi.ucm.model;

public class Mensaje {
    private long id;
    private String asunto;
    private Usuario remitente;
    private Usuario destinatario;
    private String mensaje;
    private boolean leido;
    private String fecha;
    public Mensaje(Long id,String asunto, Usuario remitente, Usuario destinatario, String mensaje, boolean leido,String fecha) {
        this.id=id;
        this.asunto=asunto;
        this.remitente=remitente;
        this.destinatario=destinatario;
        this.mensaje=mensaje;
        this.leido=leido;
        this.fecha=fecha;
    }
    public long getId()
    {
        return id;
    }
    public String getAsunto()
    {
        return asunto;
    }
    public Usuario getRemitente()
    {
        return remitente;
    }
    public Usuario getDestinatario(){
        return destinatario;
    }
    public String getMensaje()
    {
        return mensaje;
    }
    public boolean getleido()
    {
        return leido;
    }
    public String getFecha()
    {
        return fecha;
    }
}
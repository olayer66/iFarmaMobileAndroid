package fdi.ucm.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Mensaje implements Parcelable {
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
    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public String getFecha()
    {
        return fecha;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(mensaje);
        dest.writeString(asunto);
        dest.writeString(fecha);
        dest.writeParcelable(remitente,flags);
        dest.writeParcelable(destinatario,flags);
        dest.writeByte((byte) (leido ? 1 : 0));

    }
    public void readFromParcel(Parcel in) {
        id = in.readLong();
        asunto= in.readString();
        mensaje=in.readString();
        fecha= in.readString();
        leido= in.readByte() != 0;
        remitente= in.readParcelable(Usuario.class.getClassLoader());
        destinatario= in.readParcelable(Usuario.class.getClassLoader());
    }
}
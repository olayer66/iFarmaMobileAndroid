package fdi.ucm;

import android.app.Application;

import java.util.List;

import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Medico;
import fdi.ucm.model.Paciente;

/**
 * Created by joset on 20/06/2017.
 */

public class Propiedades{
    private static Propiedades mInstance= null;
    private static String id="";
    private static String role="";
    private static Medico medico=null;
    private static Paciente paciente=null;
    private static List<Medicamento> medicamentos;
    protected Propiedades(){}

    public static synchronized Propiedades getInstance(){
        if(null == mInstance){
            mInstance = new Propiedades();
        }
        return mInstance;
    }


    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        Propiedades.role = role;
    }

    //Medico
    public Medico getMedico() {
        return medico;
    }
    public void setMedico(Medico medico) {
        this.medico = medico;
    }
    //Medicamentos
    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }
    public void setMedicamentos(List<Medicamento> medicamentos) {
        Propiedades.medicamentos = medicamentos;
    }
    //Paciente
    public static Paciente getPaciente() {
        return paciente;
    }
    public static void setPaciente(Paciente paciente) {
        Propiedades.paciente = paciente;
    }
}
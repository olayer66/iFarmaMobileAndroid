package fdi.ucm;

import java.util.ArrayList;
import java.util.List;
import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Medico;
import fdi.ucm.model.Paciente;


public class Propiedades{
    private static Propiedades mInstance= null;
    private static String role="";
    private static Medico medico=null;
    private static Paciente paciente=null;
    private static ArrayList<Medicamento> medicamentos;
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
    public ArrayList<Medicamento> getMedicamentos() {
        return medicamentos;
    }
    public void setMedicamentos(ArrayList<Medicamento> medicamentos) {
        Propiedades.medicamentos = medicamentos;
    }
    //Paciente
    public Paciente getPaciente() {
        return paciente;
    }
    public void setPaciente(Paciente paciente) {
        Propiedades.paciente = paciente;
    }
}
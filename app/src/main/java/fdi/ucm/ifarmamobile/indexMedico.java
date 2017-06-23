package fdi.ucm.ifarmamobile;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import fdi.ucm.Propiedades;
import fdi.ucm.adapters.MensajeAdapter;
import fdi.ucm.adapters.PacienteAdapter;
import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Medico;
import fdi.ucm.model.Paciente;
import fdi.ucm.model.Usuario;
import fdi.ucm.volley.Conexion;

public class indexMedico extends AppCompatActivity
        implements MensajeAdapter.OnCorreoSelected
                  ,PacienteAdapter.OnPacienteSelected
                  ,DetalleCorreoFragment.OnResponderSelected
                  ,NuevoCorreoFragment.goBackCorreo
                  ,DetallePacienteFragment.OnNuevoTratamiento
                  ,NuevoTratamientoFragment.goBackTratamiento{
    private ListaPacientesFragment fragListaPacientes;
    private listaCorreoFragment fragListaCorreo;
    private DetalleCorreoFragment fragDetalleCorreo;
    private DetallePacienteFragment fragDetallePaciente;
    private NuevoCorreoFragment fragNuevoCorreo;
    private NuevoTratamientoFragment fragNuevoTratamiento;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_medico);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarMedico);
        setSupportActionBar(myToolbar);
        cargarListaPacientes();
    }
    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medico, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.agendaPac:
                cargarListaPacientes();
                return true;
            case R.id.correoMed:
                cargarMensajes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //Framents pricipales
    private void cargarListaPacientes() {
        ArrayList<Medicamento> listadoMed=Propiedades.getInstance().getMedicamentos();
        fragListaPacientes= ListaPacientesFragment.newInstance(Propiedades.getInstance().getMedico(),listadoMed);
        transaction = getSupportFragmentManager().beginTransaction();
        /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_right_exit);*/
        transaction.addToBackStack(null);
        transaction.replace(R.id.FragmentPrincipal, fragListaPacientes);
        transaction.commit();
    }
    private void cargarMensajes() {
        android.app.Fragment fragmento = getFragmentManager().findFragmentByTag("listaCorreo");
        if(fragmento==null || !fragmento.isVisible()) {
            transaction = getSupportFragmentManager().beginTransaction();
            /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit,
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit);*/
            transaction.addToBackStack(null);
            transaction.replace(R.id.FragmentPrincipal, fragListaCorreo);
            transaction.commit();
        }
    }
    //Fragments de detalle supeditamos a los fragment principales
    @Override
    public void OnCorreoSelected(String asunto, Usuario remitente, Usuario emisor, String fecha, String mensaje) {
        fragDetalleCorreo= DetalleCorreoFragment.newInstance(asunto,remitente,emisor,fecha,mensaje);
        transaction = getSupportFragmentManager().beginTransaction();
            /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit,
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit);*/
        transaction.addToBackStack(null);
        transaction.replace(R.id.FragmentPrincipal, fragDetalleCorreo);
        transaction.commit();
    }
    @Override
    public void OnPacienteSelected(Paciente paciente,ArrayList<Medicamento> medicamentos) {
        fragDetallePaciente= DetallePacienteFragment.newInstance(paciente, medicamentos);
        transaction = getSupportFragmentManager().beginTransaction();
            /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit,
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit);*/
        transaction.addToBackStack(null);
        transaction.replace(R.id.FragmentPrincipal, fragDetallePaciente);
        transaction.commit();
    }
    @Override
    public void OnResponderSelected(Usuario remitente, Usuario emisor) {
        fragNuevoCorreo = NuevoCorreoFragment.newInstance(remitente,emisor);
        transaction=getSupportFragmentManager().beginTransaction();
        /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit,
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit);*/
        transaction.addToBackStack(null);
        transaction.replace(R.id.FragmentPrincipal, fragNuevoCorreo);
        transaction.commit();
    }
    @Override
    public void OnNuevoTratamiento(Paciente paciente, ArrayList<Medicamento> medicamentos) {
        fragNuevoTratamiento = NuevoTratamientoFragment.newInstance(paciente, medicamentos);
        transaction=getSupportFragmentManager().beginTransaction();
        /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit,
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit);*/
        transaction.addToBackStack(null);
        transaction.replace(R.id.FragmentPrincipal, fragNuevoTratamiento);
        transaction.commit();
    }
    @Override
    public void goBackCorreo() {
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().popBackStack();
    }
    @Override
    public void goBackTratamiento() {
        getSupportFragmentManager().popBackStack();
    }
}
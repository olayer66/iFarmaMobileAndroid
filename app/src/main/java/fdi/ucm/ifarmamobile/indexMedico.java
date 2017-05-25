package fdi.ucm.ifarmamobile;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import fdi.ucm.adapters.MensajeAdapter;
import fdi.ucm.adapters.PacienteAdapter;
import fdi.ucm.model.Tratamiento;


public class indexMedico extends AppCompatActivity
        implements MensajeAdapter.OnCorreoSelected
                  ,PacienteAdapter.OnPacienteSelected {

    private ListaPacientesFragment fragListaPacientes;
    private listaCorreoFragment fragListaCorreo;
    private DetalleCorreoFragment fragDetalleCorreo;
    private DetallePacienteFragment fragDetallePaciente;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_medico);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarMedico);
        setSupportActionBar(myToolbar);
        fragListaPacientes = new ListaPacientesFragment();
        fragListaCorreo = new listaCorreoFragment();
        //fragDetalleCorreo=new DetalleCorreoFragment();
        if(savedInstanceState==null) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.FragmentPrincipal, fragListaPacientes);
            transaction.addToBackStack(null);
            transaction.commit();
        }
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
    private void cargarListaPacientes()
    {
        android.app.Fragment fragmento = getFragmentManager().findFragmentByTag("listaPacientes");
        if(fragmento==null || !fragmento.isVisible()) {
            transaction = getSupportFragmentManager().beginTransaction();
            /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit,
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit);*/
            transaction.addToBackStack(null);
            transaction.replace(R.id.FragmentPrincipal, fragListaPacientes);
            transaction.commit();
        }
    }
    private void cargarMensajes()
    {
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
    @Override
    public void OnCorreoSelected(String asunto, String remitente, String fecha, String mensaje) {
        fragDetalleCorreo= DetalleCorreoFragment.newInstance(asunto,remitente,fecha,mensaje);
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
    public void OnPacienteSelected(String nombre, String telefono, String email, ArrayList<Tratamiento> tratamientos)
    {
        fragDetallePaciente= DetallePacienteFragment.newInstance(nombre,email,telefono,tratamientos);
    }
}
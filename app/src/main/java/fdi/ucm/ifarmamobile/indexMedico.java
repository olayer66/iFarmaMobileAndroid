package fdi.ucm.ifarmamobile;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

import fdi.ucm.Propiedades;
import fdi.ucm.adapters.MensajeAdapter;
import fdi.ucm.adapters.PacienteAdapter;
import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Medico;
import fdi.ucm.model.Paciente;
import fdi.ucm.model.Usuario;

public class indexMedico extends AppCompatActivity
        implements MensajeAdapter.OnCorreoSelected
                  ,PacienteAdapter.OnPacienteSelected
                  ,DetalleCorreoFragment.OnResponderSelected
                  ,NuevoCorreoFragment.goBackCorreo
                  ,DetallePacienteFragment.OnNuevoTratamiento
                  ,NuevoTratamientoFragment.goBackTratamiento{
    //Control de los fragment
    private FrameLayout frameLayout;
    private FragmentTransaction transaction;
    //Dispositivo y orientacion
    private boolean tablet;
    private int orientation;
    //Datos basicos
    private Medico medico;
    private ArrayList<Medicamento> listaMed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tablet=isTablet(this);
        orientation=getResources().getConfiguration().orientation;
        if(tablet && orientation==2)
            setContentView(R.layout.activity_index_medico_tablet);
        else
            setContentView(R.layout.activity_index_medico);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarMedico);
        setSupportActionBar(myToolbar);
        //Cargamos el medico en la activity
        medico= Propiedades.getInstance().getMedico();
        //Cargamos el listado de medicamentos en la activity
        listaMed= Propiedades.getInstance().getMedicamentos();
        frameLayout = (FrameLayout) getWindow().findViewById(R.id.FragmentDetalle);
        //Cargamos el fragment principal
        cargarListaPacientes();
    }
    //Menu
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

        ListaPacientesFragment fragListaPacientes = ListaPacientesFragment.newInstance(medico.getListaPacientes(), listaMed);
        transaction = getSupportFragmentManager().beginTransaction();
        /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_right_exit);*/
        transaction.addToBackStack(null);
        if(tablet && orientation==2) {
            frameLayout.setVisibility(View.INVISIBLE);
            transaction.replace(R.id.FragmentLateral, fragListaPacientes);
        }else
            transaction.replace(R.id.FragmentPrincipal, fragListaPacientes);
        transaction.commit();
    }
    private void cargarMensajes() {
        listaCorreoFragment fragListaCorreo = listaCorreoFragment.newInstance(medico.getListaMensajes(),medico.getId());
        transaction = getSupportFragmentManager().beginTransaction();
        /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_right_exit);*/
        transaction.addToBackStack(null);
        if(tablet && orientation==2) {
            frameLayout.setVisibility(View.INVISIBLE);
            transaction.replace(R.id.FragmentLateral, fragListaCorreo);
        }else
            transaction.replace(R.id.FragmentPrincipal, fragListaCorreo);
        transaction.commit();
    }

    //pantallas
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    //Fragments de detalle supeditados a los fragment principales
    @Override
    public void OnCorreoSelected(String asunto, Usuario remitente, Usuario emisor, String fecha, String mensaje) {
        DetalleCorreoFragment fragDetalleCorreo = DetalleCorreoFragment.newInstance(asunto, remitente, emisor, fecha, mensaje);
        transaction = getSupportFragmentManager().beginTransaction();
            /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit,
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit);*/
        transaction.addToBackStack(null);
        if(tablet && orientation==2) {
            frameLayout.setVisibility(View.VISIBLE);
            transaction.replace(R.id.FragmentDetalle, fragDetalleCorreo);
        }else
            transaction.replace(R.id.FragmentPrincipal, fragDetalleCorreo);
        transaction.commit();
    }
    @Override
    public void OnPacienteSelected(Paciente paciente,ArrayList<Medicamento> medicamentos) {
        DetallePacienteFragment fragDetallePaciente = DetallePacienteFragment.newInstance(paciente, medicamentos);
        transaction = getSupportFragmentManager().beginTransaction();
            /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit,
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit);*/
        transaction.addToBackStack(null);
        if(tablet && orientation==2) {
            frameLayout.setVisibility(View.VISIBLE);
            transaction.replace(R.id.FragmentDetalle, fragDetallePaciente);
        }else
            transaction.replace(R.id.FragmentPrincipal, fragDetallePaciente);
        transaction.commit();
    }
    @Override
    public void OnResponderSelected(Usuario remitente, Usuario emisor) {
        NuevoCorreoFragment fragNuevoCorreo = NuevoCorreoFragment.newInstance(remitente, emisor);
        transaction=getSupportFragmentManager().beginTransaction();
        /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit,
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit);*/
        transaction.addToBackStack(null);
        if(tablet && orientation==2) {
            frameLayout.setVisibility(View.VISIBLE);
            transaction.replace(R.id.FragmentDetalle, fragNuevoCorreo);
        }else
            transaction.replace(R.id.FragmentPrincipal, fragNuevoCorreo);
        transaction.commit();
    }
    @Override
    public void OnNuevoTratamiento(Paciente paciente, ArrayList<Medicamento> medicamentos) {
        NuevoTratamientoFragment fragNuevoTratamiento = NuevoTratamientoFragment.newInstance(paciente, medicamentos);
        transaction=getSupportFragmentManager().beginTransaction();
        /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit,
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit);*/
        transaction.addToBackStack(null);
        if(tablet && orientation==2) {
            frameLayout.setVisibility(View.VISIBLE);
            transaction.replace(R.id.FragmentDetalle, fragNuevoTratamiento);
        }else
            transaction.replace(R.id.FragmentPrincipal, fragNuevoTratamiento);
        transaction.commit();
    }
    @Override
    public void goBackCorreo() {
        if(tablet && orientation==2)
            getSupportFragmentManager().popBackStack();
        else{
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().popBackStack();
        }
    }
    @Override
    public void goBackTratamiento() {
        getSupportFragmentManager().popBackStack();
    }

}
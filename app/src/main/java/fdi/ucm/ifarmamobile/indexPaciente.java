package fdi.ucm.ifarmamobile;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import fdi.ucm.Propiedades;
import fdi.ucm.adapters.MensajeAdapter;
import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Paciente;
import fdi.ucm.model.Usuario;

public class indexPaciente extends AppCompatActivity implements MensajeAdapter.OnCorreoSelected,
        DetalleCorreoFragment.OnResponderSelected
        ,NuevoCorreoFragment.goBackCorreo{

    private Paciente pac;
    ArrayList<Medicamento> medicamentos;
    PerfilPacienteFragment fragPerfilPac;
    TratamientoPacienteFragment tratPacFragment;

    private listaCorreoFragment fragListaCorreo;
    private DetalleCorreoFragment fragDetalleCorreo;
    private NuevoCorreoFragment fragNuevoCorreo;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_paciente);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarPaciente);
        setSupportActionBar(myToolbar);
        pac = Propiedades.getInstance().getPaciente();
        medicamentos = Propiedades.getInstance().getMedicamentos();
        //Carga el fragmento principal
        cargarPerfilPac();
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_paciente, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.perfilPac:
                cargarPerfilPac();
                return true;
            case R.id.tratamientoPac:
                mostrarTratamientosPac();
                return true;
            case R.id.correoPac:
                cargarMensajesPac();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//Perfil paciente
    private void cargarPerfilPac(){

        fragPerfilPac = PerfilPacienteFragment.newInstance(pac, medicamentos);
        transaction = getSupportFragmentManager().beginTransaction();
            /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit,
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit);*/
        transaction.addToBackStack(null);
        transaction.replace(R.id.FragmentPrincipalPac, fragPerfilPac);
        transaction.commit();
    }


//Tratamientos
    private void mostrarTratamientosPac(){
        tratPacFragment = TratamientoPacienteFragment.newInstance(pac, medicamentos);
        transaction = getSupportFragmentManager().beginTransaction();
            /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit,
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit);*/
        transaction.addToBackStack(null);
        transaction.replace(R.id.FragmentPrincipalPac, tratPacFragment);
        transaction.commit();
    }

//Correo
    private void cargarMensajesPac() {
        listaCorreoFragment fragListaCorreo = listaCorreoFragment.newInstance(pac.getListaMensajes(), pac.getId());
        transaction = getSupportFragmentManager().beginTransaction();
        /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_right_exit);*/
        transaction.addToBackStack(null);
        transaction.replace(R.id.FragmentPrincipalPac, fragListaCorreo);
        transaction.commit();
    }

    //Fragments de detalle supeditamos a los fragment principales
    @Override
    public void OnCorreoSelected(String asunto, Usuario remitente, Usuario emisor, String fecha, String mensaje) {
        fragDetalleCorreo = DetalleCorreoFragment.newInstance(asunto,remitente,emisor,fecha,mensaje);
        transaction = getSupportFragmentManager().beginTransaction();
            /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit,
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit);*/
        transaction.addToBackStack(null);
        transaction.replace(R.id.FragmentPrincipalPac, fragDetalleCorreo);
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
        transaction.replace(R.id.FragmentPrincipalPac, fragNuevoCorreo);
        transaction.commit();
    }

    @Override
    public void goBackCorreo() {
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().popBackStack();
    }
}

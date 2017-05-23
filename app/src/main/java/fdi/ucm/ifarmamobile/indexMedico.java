package fdi.ucm.ifarmamobile;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class indexMedico extends AppCompatActivity {

    ListaPacientesFragment fragListaPacientes;
    listaCorreoFragment fragListaCorreo;
    DetalleCorreoFragment fragDetalleCorreo;
    FragmentTransaction transaction;
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
            transaction.setCustomAnimations(R.anim.fragment_slide_right_exit,R.anim.fragment_slide_left_enter);
            transaction.replace(R.id.FragmentPrincipal, fragListaPacientes);
            transaction.commit();
        }
    }
    private void cargarMensajes()
    {
        android.app.Fragment fragmento = getFragmentManager().findFragmentByTag("listaCorreo");
        if(fragmento==null || !fragmento.isVisible()) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fragment_slide_right_exit, R.anim.fragment_slide_left_enter);
            transaction.replace(R.id.FragmentPrincipal, fragListaCorreo);
            transaction.commit();
        }
    }
}
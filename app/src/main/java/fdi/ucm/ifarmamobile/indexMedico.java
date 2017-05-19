package fdi.ucm.ifarmamobile;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class indexMedico extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_medico);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarMedico);
        setSupportActionBar(myToolbar);
        //Vista de las cards
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            listaCorreo fragment = new listaCorreo();
            transaction.replace(R.id.FragmentListaCorreo, fragment);
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

    }
    private void cargarMensajes()
    {

    }
}
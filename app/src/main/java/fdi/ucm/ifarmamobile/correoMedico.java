package fdi.ucm.ifarmamobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import fdi.ucm.adapters.MensajeAdapter;
import fdi.ucm.model.Mensaje;

public class correoMedico extends AppCompatActivity {
    private List<Mensaje> mensajes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correo_medico);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarMedico);
        setSupportActionBar(myToolbar);
        RecyclerView rv = (RecyclerView)findViewById(R.id.rvCorreoMedico);
        //cargamos los correos desde la BBDD
        traerMensajes();
        //introducimos los mensajes en la BBDD
        MensajeAdapter adapter = new MensajeAdapter(mensajes);
        rv.setAdapter(adapter);
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
    //menu
    private void cargarListaPacientes()
    {
        Intent myIntent = new Intent(correoMedico.this,indexPaciente.class);
        correoMedico.this.startActivity(myIntent);
    }
    private void cargarMensajes()
    {
        Intent myIntent = new Intent(correoMedico.this,correoMedico.class);
        correoMedico.this.startActivity(myIntent);
    }
    //fin menu

    //Carga con volley los mensajes desde la BBDD
    private void traerMensajes()
    {

    }
}

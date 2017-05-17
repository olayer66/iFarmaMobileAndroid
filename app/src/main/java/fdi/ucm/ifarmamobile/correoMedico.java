package fdi.ucm.ifarmamobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import fdi.ucm.adapters.MensajeAdapter;
import fdi.ucm.model.Mensaje;
import fdi.ucm.model.Usuario;

public class correoMedico extends AppCompatActivity {
    private List<Mensaje> mensajes;
    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correo_medico);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarMedico);
        setSupportActionBar(myToolbar);
        rv = (RecyclerView)findViewById(R.id.rvCorreoMedico);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        //cargamos los correos desde la BBDD
        traerMensajes();
        //introducimos los mensajes en la vista
        iniAdapter();
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
        Usuario rem=new Usuario(1,"paco","perez","234324554","usuario@algo.com");
        mensajes = new ArrayList<>();
        mensajes.add(new Mensaje("prueba1",rem,"esto es una prueba de mensaje",true));
        mensajes.add(new Mensaje("prueba2",rem,"esto es una prueba de mensaje",true));
        mensajes.add(new Mensaje("prueba3",rem,"esto es una prueba de mensaje",false));
        mensajes.add(new Mensaje("prueba4",rem,"esto es una prueba de mensaje",false));
    }
    private void iniAdapter()
    {
        MensajeAdapter adapter;
        adapter = new MensajeAdapter(mensajes);
        rv.setAdapter(adapter);
    }
}

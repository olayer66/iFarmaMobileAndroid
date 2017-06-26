package fdi.ucm.ifarmamobile;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import fdi.ucm.Propiedades;
import fdi.ucm.adapters.MensajeAdapter;
import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Paciente;
import fdi.ucm.model.Usuario;


public class indexPaciente extends AppCompatActivity implements MensajeAdapter.OnCorreoSelected,
        DetalleCorreoFragment.OnResponderSelected
        ,NuevoCorreoFragment.goBackCorreo{
    //Geolocalizacion
    private FusedLocationProviderClient mFusedLocationClient;
    //Datos basicos
    private Paciente pac;
    private ArrayList<Medicamento> medicamentos;
    //Dispositivo y orientacion
    private boolean tablet;
    private int orientation;
    //Control de los fragment
    private FrameLayout frameLayout;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tablet=isTablet(this);
        orientation=getResources().getConfiguration().orientation;
        if(tablet && orientation==2)
            setContentView(R.layout.activity_index_paciente_tablet);
        else
            setContentView(R.layout.activity_index_paciente);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarPaciente);
        setSupportActionBar(myToolbar);
        pac = Propiedades.getInstance().getPaciente();
        medicamentos = Propiedades.getInstance().getMedicamentos();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        frameLayout = (FrameLayout) getWindow().findViewById(R.id.FragmentDetallePaciente);
        //Carga el fragmento principal
        cargarPerfilPac();
        if(tablet && orientation==2)
            mostrarTratamientosPac();

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
            case R.id.farmaciasPac:
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    Propiedades.getInstance().setLocation(location);
                                    cargarfarmaciasPac();
                                }
                            });
                } else
                    permisos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //pantallas
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
//Perfil paciente
    private void cargarPerfilPac(){

        PerfilPacienteFragment fragPerfilPac = PerfilPacienteFragment.newInstance(pac, medicamentos);
        transaction = getSupportFragmentManager().beginTransaction();
            /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit,
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit);*/
        transaction.addToBackStack(null);
        if(tablet && orientation==2) {
            frameLayout.setVisibility(View.VISIBLE);
            transaction.replace(R.id.FragmentDetallePaciente, fragPerfilPac);
        }else
            transaction.replace(R.id.FragmentPrincipalPac, fragPerfilPac);
        transaction.commit();
    }
    //Tratamientos
    private void mostrarTratamientosPac(){
        TratamientoPacienteFragment tratPacFragment = TratamientoPacienteFragment.newInstance(pac, medicamentos);
        transaction = getSupportFragmentManager().beginTransaction();
            /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit,
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit);*/
        transaction.addToBackStack(null);
        if(tablet && orientation==2) {
            frameLayout.setVisibility(View.INVISIBLE);
            transaction.replace(R.id.FragmentLateralPaciente, tratPacFragment);
        }else
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
        if(tablet && orientation==2) {
            frameLayout.setVisibility(View.INVISIBLE);
            transaction.replace(R.id.FragmentLateralPaciente, fragListaCorreo);
        }else
            transaction.replace(R.id.FragmentPrincipalPac, fragListaCorreo);
        transaction.commit();
    }
    //Carga la vista del mapa de farmacias con la geolocalizacion
    private void cargarfarmaciasPac() {
        MapaFragment fragMapa = MapaFragment.newInstance();
        transaction = getSupportFragmentManager().beginTransaction();
        /*transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_right_exit);*/
        transaction.addToBackStack(null);
        if(tablet && orientation==2) {
            frameLayout.setVisibility(View.VISIBLE);
            transaction.replace(R.id.FragmentDetallePaciente, fragMapa);
        }else
            transaction.replace(R.id.FragmentPrincipalPac, fragMapa);
        transaction.commit();
    }

    //Fragments de detalle supeditamos a los fragment principales
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
            transaction.replace(R.id.FragmentDetallePaciente, fragDetalleCorreo);
        }else
            transaction.replace(R.id.FragmentPrincipalPac, fragDetalleCorreo);
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
            transaction.replace(R.id.FragmentDetallePaciente, fragNuevoCorreo);
        }else
            transaction.replace(R.id.FragmentPrincipalPac, fragNuevoCorreo);
        transaction.commit();
    }
    //Si no estan pedidos los permisos de geolocalizacion los pide
    private void permisos()
    {
        int REQUEST_CODE_ASK_PERMISSIONS = 123;
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_COARSE_LOCATION))
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);

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
}

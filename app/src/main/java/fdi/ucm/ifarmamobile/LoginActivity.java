package fdi.ucm.ifarmamobile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fdi.ucm.Propiedades;
import fdi.ucm.model.Medicamento;
import fdi.ucm.model.Medico;
import fdi.ucm.volley.Conexion;

import static android.Manifest.permission.READ_CONTACTS;
import static fdi.ucm.ifarmamobile.R.id.login;
import static fdi.ucm.ifarmamobile.R.id.usuario;

/**
 * A login screen that offers login via usuario/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    // UI references.
    private AutoCompleteTextView mUsuarioView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private SharedPreferences mPrefs;

    private final String prefixURL = Conexion.getInstance().getPrefixURL();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsuarioView = (AutoCompleteTextView) findViewById(usuario);
        populateAutoComplete();
        mPrefs = getPreferences(MODE_PRIVATE);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mUsuarioSignInButton = (Button) findViewById(R.id.usuario_sign_in_button);
        mUsuarioSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mUsuarioView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid usuario, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mUsuarioView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String usuario = mUsuarioView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid usuario address.
        if (TextUtils.isEmpty(usuario)) {
            mUsuarioView.setError(getString(R.string.error_field_required));
            focusView = mUsuarioView;
            cancel = true;
        } else if (!isUsuarioValid(usuario)) {
            mUsuarioView.setError(getString(R.string.error_invalid_usuario));
            focusView = mUsuarioView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //Peticion asincrona al servidor
            showProgress(true);
            login(usuario, password, new loginResp() {
                @Override
                public void OnRespuesta(JSONObject response) {
                    try {
                        String error = response.getString("error");
                        if (error.equals("fallo"))
                            mPasswordView.setError(getString(R.string.error_incorrect_login));
                        else if (error.equals("estado"))
                            mUsuarioView.setError(getString(R.string.error_incorrect_estado));
                        else if(error.equals("red")) {
                            String mesg= getString(R.string.error_red);
                            Toast.makeText(getApplicationContext(),mesg , Toast.LENGTH_LONG).show();
                        }else {
                            String role = response.getString("role");
                            JSONObject usu = response.getJSONObject("usuario");
                            Propiedades.getInstance().setRole(role);
                            if (role.equals("MED"))
                                Propiedades.getInstance().setMedico(Conexion.parserMedico(usu, 0));
                            else
                                Propiedades.getInstance().setPaciente(Conexion.parserPaciente(usu, 0));

                            Medico med = Propiedades.getInstance().getMedico();
                            cargarMedicamentos(new MedicamentosResp() {
                                @Override
                                public void OnRespuesta(JSONObject response) {
                                    ArrayList<Medicamento> medicamentos = new ArrayList<>();
                                    JSONArray listaMed;
                                    try {
                                        listaMed = response.getJSONArray("medicamentos");
                                        for (int i = 0; i < listaMed.length(); i++) {
                                            medicamentos.add(Conexion.parserMedicamento(listaMed.getJSONObject(i)));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Propiedades.getInstance().setMedicamentos(medicamentos);
                                    showProgress(false);
                                    iniciarSesion();
                                }
                            });
                        }
                        showProgress(false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private boolean isUsuarioValid(String usuario) {
        //TODO: Replace this with your own logic
        return usuario.contains("");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only usuario addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Nickname
                .CONTENT_ITEM_TYPE},

                // Show primary usuario addresses first. Note that there won't be
                // a primary usuario address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> usuarios = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            usuarios.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addUsuariosToAutoComplete(usuarios);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void addUsuariosToAutoComplete(List<String> usuarioAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, usuarioAddressCollection);

        mUsuarioView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Nickname.NAME,
                ContactsContract.CommonDataKinds.Nickname.NAME,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    private void iniciarSesion() {
        String role = Propiedades.getInstance().getRole();
        if (role.equals("PAC")) {
            Intent myIntent = new Intent(LoginActivity.this, indexPaciente.class);
            LoginActivity.this.startActivity(myIntent);
        } else {
            //cargamos la vista del medico
            Intent myIntent = new Intent(LoginActivity.this, indexMedico.class);
            LoginActivity.this.startActivity(myIntent);
        }
    }

    //Carga los medicamentos de la BBDD
    private void cargarMedicamentos(final MedicamentosResp callback) {
        final String LOGIN_URL = prefixURL + "medicamentos";
        JsonObjectRequest medicamentosRequest = new JsonObjectRequest(Request.Method.GET, LOGIN_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.OnRespuesta(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        JSONObject err= new JSONObject();
                        try {
                            err.put("error","red");
                            err.put("mensaje",error.getMessage());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.OnRespuesta(err);
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(medicamentosRequest);
    }
    //Realiza el login contra la BBDD
    private void login(String mUsuario, String mPassword, final loginResp callback) {
        final String LOGIN_URL = prefixURL + "login";
        JSONObject request = new JSONObject();
        try {
            request.put("usuario", mUsuario);
            request.put("password", mPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, LOGIN_URL, request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       callback.OnRespuesta(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        JSONObject err= new JSONObject();
                        try {
                            err.put("error","red");
                            err.put("mensaje",error.getMessage());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.OnRespuesta(err);
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(loginRequest);
    }
    public interface loginResp{
        void OnRespuesta(JSONObject response);
    }
    private interface MedicamentosResp{
        void OnRespuesta(JSONObject response);
    }
}

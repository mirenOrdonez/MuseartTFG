package com.example.museart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegistroActivity extends AppCompatActivity {

    /*private Button btnregistrarse;
    private EditText txtemail, txtpassword, txtnombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        btnregistrarse = findViewById(R.id.btnRegistro);
        txtnombre = findViewById(R.id.txtusuario);
        txtemail = findViewById(R.id.txtemail);
        txtpassword = findViewById(R.id.txtpassword);

        btnregistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtnombre.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingrese el usuario.", Toast.LENGTH_LONG).show();
                }
                else if (txtemail.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingrese el email.", Toast.LENGTH_LONG).show();
                }
                else if (txtpassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingrese la contraseña.", Toast.LENGTH_LONG).show();
                }
                registrarse(txtnombre.getText().toString(), txtemail.getText().toString(), txtpassword.getText().toString());
            }
        });
    }

    private void registrarse(String nombre, String email, String password) {
        Log.d("CAMPOS:",nombre + email + password);
        RequestParams params = new RequestParams();
        params.put("btnregistro", "ok");
        params.put("txtnombre", nombre);
        params.put("txtemail", email);
        params.put("txtpassword", password);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://192.168.64.2/MUSEART/procesos/apiregistro.php",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.v("REGISTRO OK", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.v("ERROR: ", errorResponse.toString());
            }
        });
        String sesion = getSession("nombre");
        if (!sesion.isEmpty()) {
            startActivity(new Intent(getApplicationContext(), ListadoActivity.class));
            finish();
        }
    }
    private void SaveSession(String nombre){
        SharedPreferences sharedPref = getSharedPreferences("registrosession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nombre", nombre);
        editor.apply();

    }
    private String getSession(String nombre) {
        SharedPreferences sharedPref = getSharedPreferences("registrosession",Context.MODE_PRIVATE);
        return sharedPref.getString(nombre, "");

    }*/

}

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

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class LoginActivity extends AppCompatActivity {

    private Button btnlogin;
    private EditText txtemail, txtpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlogin = findViewById(R.id.btnlogin);
        txtemail = findViewById(R.id.txtemail);
        txtpassword = findViewById(R.id.txtpassword);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Los campos deben estar rellenos, si no, salta el mensaje
                if (txtemail.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingrese el email.", Toast.LENGTH_LONG).show();
                }
                else if (txtpassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingrese la contraseña.", Toast.LENGTH_LONG).show();
                }
                else {
                    login(txtemail.getText().toString(), txtpassword.getText().toString());
                }
            }
        });
    }

    private void login(String email, String password) {
        RequestParams params = new RequestParams();
        params.put("btnlogin", "ok");
        params.put("txtemail", email);
        params.put("txtpassword", password);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://192.168.64.2/MUSEART/procesos/apilogin.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getBoolean("success")) {
                        Log.d("BIENVENIDO ", response.getString("nombre"));
                        saveSession(response.getString("nombre"));
                        startActivity(new Intent(getApplicationContext(), ListadoActivity.class));
                        finish();
                    }
                    else {
                        Log.d("MENSAJE: ", "No existe usuario");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("MENSAJEERROR", errorResponse.toString());
            }
        });
        String sesion = getSession("nombre");
        if (!sesion.isEmpty()) {
            startActivity(new Intent (getApplicationContext(), ListadoActivity.class));
            finish();
        }
    }

    private void saveSession(String nombre) {
        SharedPreferences sharedPref = getSharedPreferences("loginsession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nombre", nombre);
        editor.apply();
    }

    private String getSession(String nombre) {
        SharedPreferences sharedPref = getSharedPreferences("loginsesion",Context.MODE_PRIVATE);
        return sharedPref.getString(nombre, "");

    }

}

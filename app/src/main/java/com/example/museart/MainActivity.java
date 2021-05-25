package com.example.museart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.museart.clases.DbManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DbManager(getApplicationContext());

        Button btn_login = findViewById(R.id.btnLogin);
        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
            }
        });
        String sesion = getSession("nombre");
        if (!sesion.isEmpty()) {
            startActivity(new Intent (getApplicationContext(), ListadoActivity.class));
        }
    }

    private String getSession(String nombre) {
        SharedPreferences sharedPref = getSharedPreferences("loginsesion",Context.MODE_PRIVATE);
        return sharedPref.getString(nombre, "");

    }

    private void getObras() {
        RequestParams params = new RequestParams();
        params.put("app", "ok");
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://192.168.64.2/MUSEART/procesos/api.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Log.d("OBRAS", response.toString());
                    //db.borrarTabla("obras");

                    if (response.getBoolean("success")) {
                        JSONArray obras = response.getJSONArray("lista_obras");
                        for (int i = 0; i < obras.length(); i++) {
                            try {
                                JSONObject o = obras.getJSONObject(i);
                                db.insertarObras(
                                        o.getInt("id"),
                                        o.getString("titulo"),
                                        o.getString("artista"),
                                        o.getInt("annoNac"),
                                        o.getString("lugarNac"),
                                        o.getInt("annoFal"),
                                        o.getString("lugarFal"),
                                        o.getInt("publicado_en"),
                                        o.getString("periodo"),
                                        o.getString("descripcion"),
                                        o.getString("dato_curioso"),
                                        o.getString("imagen"));
                                Log.d("INSERCIÓN", " CORRECTA");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("ERROR :", errorResponse.toString());
            }
        });
    }

}

package com.example.museart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.museart.clases.DbManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class ListadoActivity extends AppCompatActivity {

    private DbManager db;
    private ListView lista;
    private Cursor c;
    private obraAdapter adaptador;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);


        db = new DbManager(getApplicationContext());


        getObras();

        //Llenamos el list view
        lista = findViewById(R.id.listaObras);
        c = db.getCursor("obras", "1");
        if (c.moveToFirst()) {
            try {
                adaptador = new obraAdapter(getApplicationContext(), c);
                lista.setAdapter(adaptador);

            } catch (Exception e) {
                Log.d("error", e.getMessage());
            }

        }
        else {
            Log.d("ERROR", "listview");
        }
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Cursor cursorObra = (Cursor) lista.getItemAtPosition(i);
                int id = cursorObra.getColumnIndexOrThrow("id");
                //Log.d("id", String.valueOf(id));
                startActivity(new Intent(getApplicationContext(), ObraActivity.class).putExtra("id", id));

            }
        });
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
                    db.borrarTabla("obras");
                    //Log.d("OBRAS", response.toString());
                    if (response.getBoolean("success")) {
                        JSONArray obras = response.getJSONArray("listaObras");
                        for (int i=0; i < obras.length(); i++) {
                            JSONObject o = obras.getJSONObject(i);
                            db.insertarObras(o.getInt("id"),
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
                            Log.d("Se", " muestra la lista");
                        }
                    }


            } catch (Exception e) {
                    Log.d("ERROR: ", e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("ERROR: ", errorResponse.toString());
            }
        });


    }

    //Crea la opción de salir en el menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    //Para los tres puntitos, menú para cerrar la sesión.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cerrar:
                SharedPreferences sharedPref = getSharedPreferences("loginsesion", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("nombre", "");
                editor.apply();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

class obraAdapter extends CursorAdapter {
    public obraAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.lista_obras, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView _urlimagen = view.findViewById(R.id.urlimagen);
        TextView _titulo = view.findViewById(R.id.titulo);
        TextView _artista = view.findViewById(R.id.artista);


        _titulo.setText(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
        _artista.setText(cursor.getString(cursor.getColumnIndexOrThrow("artista")));

        Picasso.with(context)
                .load("http://192.168.64.2/MUSEART/img/"+cursor.getString(cursor.getColumnIndexOrThrow("imagen")))
                .into(_urlimagen, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
    }

}

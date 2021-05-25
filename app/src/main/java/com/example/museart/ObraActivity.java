package com.example.museart;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.museart.clases.DbManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ObraActivity extends AppCompatActivity {

    private DbManager db;
    private Cursor c;
    private ImageView _imagen;
    private TextView _titulo, _artista, _annoNac, _lugarNac, _annoFal, _lugarFal, _publicado_en, _periodo, _descripcion, _dato_curioso;
    private ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obra);
        _imagen = findViewById(R.id.urlimagen);
        _titulo = findViewById(R.id.titulo);
        _artista = findViewById(R.id.artista);
        _annoNac = findViewById(R.id.annoNac);
        _lugarNac = findViewById(R.id.lugarNac);
        _annoFal = findViewById(R.id.annoFal);
        _lugarFal = findViewById(R.id.lugarFal);
        _publicado_en = findViewById(R.id.publicado_en);
        _periodo = findViewById(R.id.periodo);
        _descripcion = findViewById(R.id.descripcion);
        _dato_curioso = findViewById(R.id.dato_curioso);

        int id = getIntent().getExtras().getInt("id");

        db = new DbManager(getApplicationContext());

        c = db.getCursor("obras", "_id="+id);
        if (c.moveToFirst()) {
            do {
                _titulo.setText(c.getString(c.getColumnIndexOrThrow("titulo")));
                _artista.setText(c.getString(c.getColumnIndexOrThrow("artista")));
                _annoNac.setText(c.getInt(c.getColumnIndexOrThrow("annoNac")));
                _lugarNac.setText(c.getString(c.getColumnIndexOrThrow("titulo")));
                _annoFal.setText(c.getInt(c.getColumnIndexOrThrow("annoFal")));
                _lugarFal.setText(c.getString(c.getColumnIndexOrThrow("lugarFal")));
                _publicado_en.setText(c.getInt(c.getColumnIndexOrThrow("publicado_en")));
                _periodo.setText(c.getString(c.getColumnIndexOrThrow("periodo")));
                _descripcion.setText(c.getString(c.getColumnIndexOrThrow("descripcion")));
                _dato_curioso.setText(c.getString(c.getColumnIndexOrThrow("dato_curioso")));

                Picasso.with(getApplicationContext()).load("http://192.168.64.2/MUSEART/img"+c.getString(c.getColumnIndexOrThrow("imagen")))
                        .into(_imagen, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
            } while (c.moveToFirst());

        }

    }
}

package com.example.museart.clases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DbManager {
    public static final String TABLA_OBRAS = "obras";
    public static final String OBRAS_ID = "id";
    public static final String OBRAS_TITULO = "titulo";
    public static final String OBRAS_ARTISTA = "artista";
    public static final String OBRAS_ANNONAC = "annoNac";
    public static final String OBRAS_LUGARNAC = "lugarNac";
    public static final String OBRAS_ANNOFAL = "annoFal";
    public static final String OBRAS_LUGARFAL = "lugarFal";
    public static final String OBRAS_PUBLICADO_EN = "publicado_en";
    public static final String OBRAS_PERIODO = "periodo";
    public static final String OBRAS_DESCRIPCION = "descripcion";
    public static final String OBRAS_DATO_CURIOSO = "dato_curioso";
    public static final String OBRAS_IMAGEN = "imagen";


    public static final String TABLA_OBRAS_CREATE = "CREATE TABLE obras ("+
            "_id INTEGER PRIMARY KEY, " +
            "titulo text not null, " +
            "artista text not null, " +
            "annoNac integer not null, " +
            "lugarNac text not null, " +
            "annoFal integer not null, " +
            "lugarFal text not null, " +
            "publicado_en integer not null, " +
            "periodo text not null, " +
            "descripcion text not null, " +
            "dato_curioso text not null, " +
            "imagen text not null);";


    private DbHelper helper;
    private SQLiteDatabase db;

    public DbManager(Context context) {
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    public DbManager open() throws SQLException {
        db = helper.getWritableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    //CREAR LA TABLA OBRAS
    private ContentValues generarObras(int id, String titulo, String artista, int annoNac, String lugarNac, int annoFal,
            String lugarFal,int publicado_en, String periodo, String descripcion, String dato_curioso, String imagen) {
        ContentValues init = new ContentValues();
        init.put(OBRAS_ID, id);
        init.put(OBRAS_TITULO, titulo);
        init.put(OBRAS_ARTISTA, artista);
        init.put(OBRAS_ANNONAC, annoNac);
        init.put(OBRAS_LUGARNAC, lugarNac);
        init.put(OBRAS_ANNOFAL, annoFal);
        init.put(OBRAS_LUGARFAL, lugarFal);
        init.put(OBRAS_PUBLICADO_EN, publicado_en);
        init.put(OBRAS_PERIODO, periodo);
        init.put(OBRAS_DESCRIPCION, descripcion);
        init.put(OBRAS_DATO_CURIOSO, dato_curioso);
        init.put(OBRAS_IMAGEN, imagen);
        return init;
    }

    public long insertarObras (int id, String titulo, String artista, int annoNac, String lugarNac, int annoFal,
                               String lugarFal,int publicado_en, String periodo, String descripcion,
                               String dato_curioso, String imagen) {

        return db.insert(TABLA_OBRAS, null, generarObras(id, titulo, artista, annoNac, lugarNac, annoFal,
                             lugarFal, publicado_en,  periodo,  descripcion, dato_curioso, imagen));
    }

    public void borrarTabla (String tabla) {
        db.delete(tabla, null, null);
    }

    public Cursor getCursor(String tabla, String condicion) throws SQLException {
        String q = "SELECT rowid _id,* FROM " + tabla + " WHERE " + condicion;
        Cursor cursor = db.rawQuery(q, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}

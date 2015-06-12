package com.andremartinez.losverdes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Andres Felipe on 01/06/2015.
 */
public class DataBaseManager {
    public static final String TABLE_NAME = "sedes";
    public static final String SD_ID = "_id";
    public static final String SD_NAME = "nombre";
    public static final String SD_LAT = "latitud";
    public static final String SD_LONG = "longitud";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + SD_ID + " integer primary key autoincrement,"
            + SD_NAME + " text not null,"
            + SD_LAT + " text,"
            + SD_LONG + " text);";

    DbHelper helper;
    SQLiteDatabase db;

    public DataBaseManager(Context context){    //Constructor
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    public ContentValues generarContentValues(String Nombre, String Latitud, String Longitud){
        ContentValues valores = new ContentValues();

        valores.put(SD_NAME, Nombre);
        valores.put(SD_LAT, Latitud);
        valores.put(SD_LONG, Longitud);

        return valores;
    }

    public void insertar(String Nombre, String Latitud, String Longitud){
        //INSERT INTO sedes VALUES (null, 'UdeA', Lat, Long)
        db.insert(TABLE_NAME, null, generarContentValues(Nombre,Latitud,Longitud));
    }

    public void eliminar(String nombre){
        db.delete(TABLE_NAME, SD_NAME+"=?", new String[] {nombre});
    }

    public void modificar_latlong(String nombre, String nuevaLatit, String nuevaLong){
        db.update(TABLE_NAME, generarContentValues(nombre,nuevaLatit,nuevaLong),SD_NAME + "=?", new String[] {nombre});
    }

    public Cursor buscar_name(String nombre){
        String[] columnas = new String[] {SD_ID, SD_NAME, SD_LAT, SD_LONG};

        return db.query(TABLE_NAME, columnas, SD_NAME + "=?", new String[] {nombre},null,null,null);
    }

  public Cursor cargarCursor(){
        //query(Table,columns,Stringselection, Stringselectionargs,StringGroupBY,StringHaving,StringOrderBy)
        String[] columnas= new String[] {SD_ID, SD_NAME, SD_LAT, SD_LONG};
        return db.query(TABLE_NAME,columnas,null,null,null,null,null);
    }

}

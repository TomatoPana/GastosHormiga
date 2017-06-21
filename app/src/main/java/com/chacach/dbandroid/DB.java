package com.chacach.dbandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {

    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Prueba", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table datos(nombre text, gasto text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public String guardar(String nombre, String gasto, String fecha){
        String mensaje="";
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contenedor = new ContentValues();
        String resultado = "Gasto: " + nombre + " Cantidad: $" + gasto + " el " + fecha;
        contenedor.put("nombre", resultado);
        contenedor.put("gasto", gasto);
        try {
            if(nombre.equalsIgnoreCase("") || gasto.equalsIgnoreCase(""))
                mensaje="Campos vacios, revise";
            else{
                database.insertOrThrow("datos",null,contenedor);
                mensaje="Ingresado Correctamente";
            }
        }catch (SQLException e){
            mensaje="No Ingresado";
        }
        database.close();
        return mensaje;
    }

    public ArrayList llenar_lv(){
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM datos";
        Cursor registros = database.rawQuery(q,null);
        if(registros.moveToFirst()){
            do{
                lista.add(registros.getString(0));
            }while(registros.moveToNext());
        }
        return lista;

    }

    public String getGastosTotales() {
        String gastosTotales="";
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT SUM(gasto) as ResultadoGasto FROM datos";
        Cursor registros = database.rawQuery(q,null);
        if(registros.moveToFirst()){
            do{
                gastosTotales = registros.getString(0);
            }while(registros.moveToNext());
        }
        return gastosTotales;

    }

}


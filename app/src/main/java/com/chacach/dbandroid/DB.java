package com.chacach.dbandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clase DB, define los metodos de conexion a la base de datos interna de la aplicacion, genera las consultas y retorna los elementos que se seleccionan
 * @Author Moises David Lozano Bobadilla & Hugo Rodrigo Puga Mora
 * La clase extiende de SQLiteOneHelper, para mas infomacion consulte
 * @see SQLiteOpenHelper
 */

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {
    /**
     * Constructor de la base de datos, intenta crear una base de datos
     * @param context El contexto de la base de datos
     * @param name Nombre de la base de datos
     * @param factory Conector por usar para las consultas, se utiliza por defecto SQLiteDatabase.CursorFactory
     * @param version Version de la base de datos a usar (Apto para el gestionador de versiones)
     */
    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Prueba", factory, 1);
    }

    /**
     * Metodo de creacion de la base de datos, crea una tabla llamada datos en caso de no exixtir, se ejecuta la funcion al momento de llamar a la base de datos y esta marque nulo
     * @param db Base de datos a consultar
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table datos(nombre text, gasto text)");
    }

    /**
     * Metodo para la actualizadion de datos, exigido por la interfaz SQLiteOpenHelper
     * @param sqLiteDatabase Unknown
     * @param i Unknown
     * @param i1 Unknown
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * Funcion para guardar los datos especificados en la base de datos, intenta castear los datos a los específicos de la base de datos
     * @param nombre Nombre del gasto a ser guardado
     * @param gasto Gasto a ser guardado
     * @param fecha Fecha del gasto a ser guardado
     * @return mensaje de estado de la consulta, en caso de que estén vacios los campos u ocurra un error de conexion se lanza un mensaje de error, en caso contrario se manda un mensaje de confirmación
     * @throws SQLException
     */
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

    /**
     * Funcion para retornar todos los datos ingresados en la base de datos, realiza un punteo de todos las filas de la tabla
     * @return ArrayList que contiene todos los datos debidamente casteados al tipo String
     */
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

    /**
     * Funcion para obtener el gasto total de todos los elementos de la tabla, usando las herramientas que nos da MYSQL
     * @return Resultado de los gastos totales almacenados debidamente casteado al tipo String
     */
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


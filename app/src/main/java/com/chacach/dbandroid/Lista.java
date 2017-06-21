package com.chacach.dbandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Clase Lista, usada para controlar la vista activity_lista.xml modificando los datos cuando sean necesario y ejecutando las debidas consultas
 * Extiende de AppCompactActivity, para mas informacion vea
 * @see AppCompatActivity
 */

public class Lista extends AppCompatActivity {
    ListView lv ;
    TextView gastosTotales;
    ArrayList<String> lista;
    ArrayAdapter adaptador;
    @Override
    /**
     * Funcion onCreate, cargada cuando la vista activity_lista.xml se encuentra en proceso de renderización
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        lv = (ListView)findViewById(R.id.lista);
        gastosTotales = (TextView)findViewById(R.id.gastosTotales);
        DB db = new DB(getApplicationContext(),null,null,1);
        lista = db.llenar_lv();
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,lista);
        lv.setAdapter(adaptador);
        gastosTotales.setText("Gastos totales en el mes $" + db.getGastosTotales());

    }

}

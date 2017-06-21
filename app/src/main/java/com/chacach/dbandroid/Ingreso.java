package com.chacach.dbandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Clase Ingreso
 * Usada para controlar la Vista activity_ingreso.xml modificando los datos cuando sean necesario y ejecutando las debidas consultas
 * Extiende de AppCompactActivity, para mas informacion vea
 * @see AppCompatActivity
 */
public class Ingreso extends AppCompatActivity {
    EditText Enombre,Egasto;
    Button guardar,buscar;
    /**
     * Funcion onCreate, cargada cuando la vista activity_ingreso.xml se encuentra en proceso de renderización
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);
        Enombre=(EditText)findViewById(R.id.nombre);
        Egasto=(EditText)findViewById(R.id.gasto);
        guardar=(Button)findViewById(R.id.guardar);
        buscar=(Button)findViewById(R.id.buscar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                DB db= new DB(getApplicationContext(),null,null,1);
                String nombre = Enombre.getText().toString();
                String gasto = Egasto.getText().toString();
                String fecha = df.format(cal.getTime());
                String mensaje =db.guardar(nombre, gasto, fecha);
                Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
            }
        });
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(getApplicationContext(),Lista.class);
                startActivity(intento);
            }
        });
    }

}

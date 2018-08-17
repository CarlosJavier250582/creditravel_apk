package com.example.carlosje.creditravel;

/**
 * Created by carlosje on 8/15/2018.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlosje on 4/7/2018.
 */

public class secionActivity extends AppCompatActivity {

    private ListView listSC;
    private List items = new ArrayList();


    private String tipo,SC_Sel,ActCodResult,AC_Sel,Comp_sel,Act_sel;



    ListView simpleList;


    String Secc[] = {"Aguascalientes", "Baja California Norte", "Baja California Sur", "Campeche", "Chiapas", "Chihuahua", "Ciudad de México", "Coahuila", "Colima", "Durango", "Estado de México", "Guanajuato", "Guerrero", "Hidalgo", "Jalisco", "Michoacán", "Morelos", "Nayarit", "Nuevo León", "Oaxaca", "Puebla", "Querétaro", "Quintana Roo", "San Luis Potosí", "Sinaloa", "Sonora", "Tabasco", "Tamaulipas", "Tlaxcala", "Veracruz", "Yucatán", "Zacatecas" };
    String SeccDesc[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seccion_cod);

        tipo = getIntent().getStringExtra("tipo");





        if (tipo.equals("seccion")){

            final int requestCode = 1;

            simpleList =  (ListView) findViewById(R.id.listSC);


            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), Secc, SeccDesc);
            simpleList.setAdapter(customAdapter);


            simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final int pos = position;

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("seccion_seleccion",Secc[pos]);

                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();

                }
            });

        }



    }




}

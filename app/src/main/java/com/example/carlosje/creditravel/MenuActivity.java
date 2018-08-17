package com.example.carlosje.creditravel;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



public class MenuActivity extends AppCompatActivity  {
    private String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        usuario = getIntent().getStringExtra("usuario");
    }



    public  void nuevo(View view){

        Intent activity_captura = new Intent(getApplicationContext(), CapturaActivity.class);
        activity_captura.putExtra("usuario", usuario);
        startActivity(activity_captura);
    }

}

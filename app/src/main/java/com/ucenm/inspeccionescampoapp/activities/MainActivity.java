package com.ucenm.inspeccionescampoapp.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ucenm.inspeccionescampoapp.R;
import com.ucenm.inspeccionescampoapp.utils.SessionManager;
import android.content.Intent;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {


    TextView tvBienvenida;
    Button btnNuevaInspeccion;
    Button btnHistorial;

    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);


        tvBienvenida = findViewById(R.id.tvBienvenida);
        btnNuevaInspeccion = findViewById(R.id.btnNuevaInspeccion);


        btnNuevaInspeccion.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    NuevaInspeccionActivity.class
            );

            startActivity(intent);

        });

        btnHistorial = findViewById(R.id.btnHistorial);


        btnHistorial.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    ListaInspeccionesActivity.class
            );

            startActivity(intent);

        });

        session = new SessionManager(this);


        tvBienvenida.setText(
                "Bienvenido: " + session.getNombre()
        );

    }
}
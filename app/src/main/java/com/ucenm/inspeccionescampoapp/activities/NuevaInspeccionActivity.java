package com.ucenm.inspeccionescampoapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ucenm.inspeccionescampoapp.R;
import com.ucenm.inspeccionescampoapp.api.ApiClient;
import com.ucenm.inspeccionescampoapp.api.ApiService;
import com.ucenm.inspeccionescampoapp.responses.InspeccionResponse;
import com.ucenm.inspeccionescampoapp.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;



public class NuevaInspeccionActivity extends AppCompatActivity {

    EditText etTitulo;
    EditText etDescripcion;
    EditText etLatitud;
    EditText etLongitud;
    EditText etEstado;
    Button btnGuardarInspeccion;
    ApiService apiService;
    SessionManager session;
    FusedLocationProviderClient fusedLocationClient;
    String latitud = "";
    String longitud = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nueva_inspeccion);


        etTitulo = findViewById(R.id.etTitulo);
        etDescripcion = findViewById(R.id.etDescripcion);
        etLatitud = findViewById(R.id.etLatitud);
        etLongitud = findViewById(R.id.etLongitud);
        etEstado = findViewById(R.id.etEstado);

        btnGuardarInspeccion =
                findViewById(R.id.btnGuardarInspeccion);


        apiService = ApiClient.getClient()
                .create(ApiService.class);


        session = new SessionManager(this);
        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);


        obtenerUbicacion();


        btnGuardarInspeccion.setOnClickListener(v -> {

            crearInspeccion();

        });


    }


    private void crearInspeccion(){


        String usuarioId = session.getId();

        String titulo = etTitulo.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        String latitud = this.latitud;
        String longitud = this.longitud;
        String estado = etEstado.getText().toString();


        apiService.crearInspeccion(
                usuarioId,
                titulo,
                descripcion,
                latitud,
                longitud,
                estado
        ).enqueue(new Callback<InspeccionResponse>() {


            @Override
            public void onResponse(
                    Call<InspeccionResponse> call,
                    Response<InspeccionResponse> response) {


                if(response.isSuccessful()){


                    Toast.makeText(
                            NuevaInspeccionActivity.this,
                            response.body().getMensaje(),
                            Toast.LENGTH_LONG
                    ).show();


                    finish();


                }


            }


            @Override
            public void onFailure(
                    Call<InspeccionResponse> call,
                    Throwable t) {


                Toast.makeText(
                        NuevaInspeccionActivity.this,
                        "Error: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();

            }


        });


    }
    private void obtenerUbicacion(){


        if(ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED){


            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    300
            );


            return;

        }



        fusedLocationClient
                .getLastLocation()
                .addOnSuccessListener(location -> {


                    if(location != null){


                        latitud =
                                String.valueOf(
                                        location.getLatitude()
                                );


                        longitud =
                                String.valueOf(
                                        location.getLongitude()
                                );


                        etLatitud.setText(latitud);

                        etLongitud.setText(longitud);


                    }else{


                        Toast.makeText(
                                this,
                                "No se pudo obtener ubicación",
                                Toast.LENGTH_SHORT
                        ).show();


                    }


                });


    }


}
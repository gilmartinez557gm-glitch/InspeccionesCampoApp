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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.Intent;


public class EditarInspeccionActivity extends AppCompatActivity {


    EditText etTituloEditar;
    EditText etDescripcionEditar;
    EditText etLatitudEditar;
    EditText etLongitudEditar;
    EditText etEstadoEditar;

    Button btnActualizarInspeccion;


    ApiService apiService;


    String inspeccionId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editar_inspeccion);


        etTituloEditar = findViewById(R.id.etTituloEditar);
        etDescripcionEditar = findViewById(R.id.etDescripcionEditar);
        etLatitudEditar = findViewById(R.id.etLatitudEditar);
        etLongitudEditar = findViewById(R.id.etLongitudEditar);
        etEstadoEditar = findViewById(R.id.etEstadoEditar);

        btnActualizarInspeccion =
                findViewById(R.id.btnActualizarInspeccion);


        apiService = ApiClient.getClient()
                .create(ApiService.class);


        inspeccionId =
                getIntent().getStringExtra("id");


        etTituloEditar.setText(
                getIntent().getStringExtra("titulo")
        );

        etDescripcionEditar.setText(
                getIntent().getStringExtra("descripcion")
        );

        etLatitudEditar.setText(
                getIntent().getStringExtra("latitud")
        );

        etLongitudEditar.setText(
                getIntent().getStringExtra("longitud")
        );

        etEstadoEditar.setText(
                getIntent().getStringExtra("estado")
        );


        btnActualizarInspeccion.setOnClickListener(v -> {

            actualizarInspeccion();

        });


    }


    private void actualizarInspeccion(){
        Toast.makeText(
                this,
                "ID: " + inspeccionId +
                        "\nTitulo: " + etTituloEditar.getText().toString() +
                        "\nDescripcion: " + etDescripcionEditar.getText().toString() +
                        "\nEstado: " + etEstadoEditar.getText().toString(),
                Toast.LENGTH_LONG
        ).show();


        apiService.editarInspeccion(

                inspeccionId,

                etTituloEditar.getText().toString(),

                etDescripcionEditar.getText().toString(),

                etLatitudEditar.getText().toString(),

                etLongitudEditar.getText().toString(),

                etEstadoEditar.getText().toString()


        ).enqueue(new Callback<InspeccionResponse>() {


            @Override
            public void onResponse(
                    Call<InspeccionResponse> call,
                    Response<InspeccionResponse> response) {


                if(response.isSuccessful()){

                    Toast.makeText(
                            EditarInspeccionActivity.this,
                            "Inspección actualizada",
                            Toast.LENGTH_LONG
                    ).show();


                    Intent intent = new Intent(
                            EditarInspeccionActivity.this,
                            ListaInspeccionesActivity.class
                    );

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);

                    finish();

                }


            }


            @Override
            public void onFailure(
                    Call<InspeccionResponse> call,
                    Throwable t) {


                Toast.makeText(
                        EditarInspeccionActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();


            }


        });


    }


}